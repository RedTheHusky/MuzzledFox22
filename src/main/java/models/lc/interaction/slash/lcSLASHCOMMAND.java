package models.lc.interaction.slash;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.la.ForCleanup1;
import models.lcGlobalHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import org.apache.log4j.Logger;

import java.util.Arrays;

@Deprecated
@ForCleanup1
@DeprecatedSince("11/24/21")
public class lcSLASHCOMMAND {
    Logger logger = Logger.getLogger(getClass());
    //https://discord.com/developers/docs/interactions/slash-commands
    Guild gGuild;
    long gID=0L, gApplication_id=0L;String gApplication_Token="";
    boolean flagCreate=false,flagEdit=false,flagDelete=false;
    public lcSLASHCOMMAND(){
        String fName="build";
        try {
            logger.info(fName+".build");

        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(lcGlobalHelper global,long id, Guild guild){
        String fName="build.guild";
        try {
            logger.info(fName+".build");
            gApplication_id= Long.parseLong(global.configfile.getBot().getId());
            gApplication_Token=global.configfile.getBot().getToken();
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".id="+id);
            logger.info(fName+".guild="+guild.getName()+"("+guild.getId()+")");
            get(id,guild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(lcGlobalHelper global,long id){
        String fName="build.global";
        try {
            gApplication_id= Long.parseLong(global.configfile.getBot().getId());
            gApplication_Token=global.configfile.getBot().getToken();
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".id="+id);
            get(id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(lcGlobalHelper global,String name, Guild guild){
        String fName="build.guild";
        try {
            logger.info(fName+".build");
            gApplication_id= Long.parseLong(global.configfile.getBot().getId());
            gApplication_Token=global.configfile.getBot().getToken();
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".name="+name);
            logger.info(fName+".guild="+guild.getName()+"("+guild.getId()+")");
            get(name,guild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(lcGlobalHelper global,String name){
        String fName="build.global";
        try {
            logger.info(fName+".build");
            gApplication_id= Long.parseLong(global.configfile.getBot().getId());
            gApplication_Token=global.configfile.getBot().getToken();
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".name="+name);
            get(name);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(long application_id, String application_token, long id, Guild guild){
        String fName="build.guild";
        try {
            logger.info(fName+".build");
            gApplication_id= application_id;
            gApplication_Token=application_token;
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".id="+id);
            logger.info(fName+".guild="+guild.getName()+"("+guild.getId()+")");
            get(id,guild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(long application_id, String application_token, long id){
        String fName="build.global";
        try {
            gApplication_id= application_id;
            gApplication_Token=application_token;
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".id="+id);
            get(id);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(long application_id, String application_token, String name, Guild guild){
        String fName="build.guild";
        try {
            logger.info(fName+".build");
            gApplication_id= application_id;
            gApplication_Token=application_token;
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".name="+name);
            logger.info(fName+".guild="+guild.getName()+"("+guild.getId()+")");
            get(name,guild);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcSLASHCOMMAND(long application_id, String application_token, String name){
        String fName="build.global";
        try {
            logger.info(fName+".build");
            gApplication_id= application_id;
            gApplication_Token=application_token;
            logger.info(fName+".application_id="+gApplication_id);
            logger.info(fName+".application_token="+gApplication_Token);
            logger.info(fName+".name="+name);
            get(name);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    JSONArray jsonGet=new JSONArray();JSONObject jsonPatch=new JSONObject(),jsonDelete=new JSONObject();
    JSONObject jsonCommand=new JSONObject();
    static public String keyApplication_Id="application_id",keyId="id",keyName="name";
    public JSONArray get( Guild guild){
        String fName="get";
        try {
            gID=0L;gGuild=null;
            logger.info(fName+".application_id="+gApplication_id+"guild="+guild.getId());
            String url = "https://discord.com/api/v8/applications/!app_id/guilds/!guild_id/commands";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!guild_id",guild.getId());
            logger.info(fName+".application_id="+gApplication_id+"guild="+guild.getId());
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONArray body=new JSONArray();
            try {
                body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
                jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return null;
            }else{
                logger.info(fName+".got");
                return body;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONArray get(){
        String fName="get";
        try {
            gID=0L;gGuild=null;
            logger.info(fName+".application_id="+gApplication_id);
            String url = "https://discord.com/api/v8/applications/!app_id/commands";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id));
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONArray body=new JSONArray();
            try {
                body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
                jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return null;
            }else{
                logger.info(fName+".got");
                return body;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean get( long id, Guild guild){
        String fName="get";
        try {
            gID=0L;gGuild=null;
            logger.info(fName+".application_id="+gApplication_id+", id="+id+", guild="+guild.getId());
            String url = "https://discord.com/api/v8/applications/!app_id/guilds/!guild_id/commands";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!guild_id",guild.getId());
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONArray body=new JSONArray();
            try {
                body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got");
                for(int i=0;i<body.length();i++){
                    try {
                        JSONObject command=body.getJSONObject(i);
                        logger.info(fName+".command["+i+"]="+command.toString());
                        if(command.getString(keyId).equals(Long.toString(id))&&command.getString(keyApplication_Id).equals(Long.toString(gApplication_id))){
                            logger.info(fName+".found>true");
                           gID=id;gGuild=guild;
                            jsonCommand=command;
                            return true;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+".not found>false");
                jsonGet=body;
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean get( long id){
        String fName="get";
        try {
            gID=0L;gGuild=null;
            logger.info(fName+".application_id="+gApplication_id+", id="+id);
            String url = "https://discord.com/api/v8/applications/!app_id/commands";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id));
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONArray body=new JSONArray();
            try {
                body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got");
                for(int i=0;i<body.length();i++){
                    try {
                        JSONObject command=body.getJSONObject(i);
                        logger.info(fName+".command["+i+"]="+command.toString());
                        if(command.getString(keyId).equals(Long.toString(id))&&command.getString(keyApplication_Id).equals(Long.toString(gApplication_id))){
                            logger.info(fName+".found>true");
                            gID=id;
                            jsonCommand=command;
                            return true;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+".not found>false");
                jsonGet=body;
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean get(String name, Guild guild){
        String fName="get";
        try {
            gID=0L;gGuild=null;
            logger.info(fName+".application_id="+gApplication_id+", name="+name+", guild="+guild.getId());
            String url = "https://discord.com/api/v8/applications/!app_id/guilds/!guild_id/commands";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!guild_id",guild.getId());
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONArray body=new JSONArray();
            try {
                body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got");
                for(int i=0;i<body.length();i++){
                    try {
                        JSONObject command=body.getJSONObject(i);
                        logger.info(fName+".command["+i+"]="+command.toString());
                        if(command.getString(keyName).equals(name)&&command.getString(keyApplication_Id).equals(Long.toString(gApplication_id))){
                            logger.info(fName+".found>true");
                            gID= Long.parseLong(command.getString(keyId));gGuild=guild;
                            jsonCommand=command;
                            return true;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+".not found>false");
                jsonGet=body;
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean get( String name){
        String fName="get";
        try {
            gID=0L;
            logger.info(fName+".application_id="+gApplication_id+", name="+name);
            String url = "https://discord.com/api/v8/applications/!app_id/commands";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id));
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.get(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONArray body=new JSONArray();
            try {
                body=jsonResponse.getBody().getArray();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got");
                for(int i=0;i<body.length();i++){
                    try {
                        JSONObject command=body.getJSONObject(i);
                        logger.info(fName+".command["+i+"]="+command.toString());
                        if(command.getString(keyName).equals(name)&&command.getString(keyApplication_Id).equals(Long.toString(gApplication_id))){
                            logger.info(fName+".found>true");
                            gID= Long.parseLong(command.getString(keyId));
                            jsonCommand=command;
                            return true;
                        }
                    }catch (Exception e){
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    }
                }
                logger.info(fName+".not found>false");
                jsonGet=body;
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public JSONArray getJsonGet(){
        String fName="getJsonGet";
        try {
            return jsonGet;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJsonCommand(){
        String fName="getJsonCommand";
        try {
            return jsonCommand;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean ifExists() {
        String fName="isGot";
        try {
            return gID!=0L;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isGlobal() {
        String fName="isGlobal";
        try {
            if(gID==0L)return false;
            return gGuild==null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isGuild() {
        String fName="isGuild";
        try {
            if(gID==0L)return false;
            return gGuild!=null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Guild getGuild() {
        String fName="getGuild";
        try {
            if(gID==0L)return null;
            return gGuild;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getId() {
        String fName="getID";
        try {
            return gID;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public long getApplication_id() {
        String fName="getApplication_id";
        try {
            return gApplication_id;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public String getName() {
        String fName="getName";
        try {
            if(gID==0L)return null;
            return jsonCommand.getString(keyName);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public boolean edit(JSONObject jsonObject){
        String fName="edit";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            if(gID==0L)return  false;
            boolean response=false;
            if(gGuild==null){
                response=edit(jsonObject,gID);
            }else{
                response=edit(jsonObject,gID,gGuild);
            }
            logger.info(fName+".response="+response);
            return  response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean edit(JSONObject jsonObject, long id, Guild guild){
        String fName="edit";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            logger.info(fName+".application_id="+gApplication_id+", id="+id+", guild="+guild.getId());
            String url = "https://discord.com/api/v8/applications/!app_id/guilds/!guild_id/commands/!command_id";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!command_id", String.valueOf(gID)).replaceAll("!guild_id",guild.getId());
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.patch(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got");
                try {
                    if(body.getString(keyId).equals(Long.toString(id))&&body.getString(keyApplication_Id).equals(Long.toString(gApplication_id))){
                        logger.info(fName+".found>true");
                        jsonCommand=body;
                        return true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+".not found>false");
                jsonPatch=body;
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean edit(JSONObject jsonObject, long id){
        String fName="edit";
        try {
            logger.info(fName+".jsonObject="+jsonObject.toString());
            logger.info(fName+".application_id="+gApplication_id+", id="+id);
            String url = "https://discord.com/api/v8/applications/!app_id/commands/!command_id";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!command_id", String.valueOf(gID));
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.patch(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .body(jsonObject.toString())
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got");
                try {
                    if(body.getString(keyId).equals(Long.toString(id))&&body.getString(keyApplication_Id).equals(Long.toString(gApplication_id))){
                        logger.info(fName+".found>true");
                        jsonCommand=body;
                        return true;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                logger.info(fName+".not found>false");
                jsonPatch=body;
                return false;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    public boolean delete(){
        String fName="delete";
        try {
            logger.info(fName+".delete");
            if(gID==0L)return  false;
            boolean response=false;
            if(gGuild==null){
                response=delete(gID);
            }else{
                response=delete(gID,gGuild);
            }
            logger.info(fName+".response="+response);
            return  response;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean delete(long id, Guild guild){
        String fName="delete";
        try {
            logger.info(fName+".application_id="+gApplication_id+", id="+id+", guild="+guild.getId());
            String url = "https://discord.com/api/v8/applications/!app_id/guilds/!guild_id/commands/!command_id";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!command_id", String.valueOf(gID)).replaceAll("!guild_id",guild.getId());
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.delete(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got"); gID=0L;gGuild=null;
                jsonDelete=body;
                return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean delete(long id){
        String fName="delete";
        try {
            logger.info(fName+".application_id="+gApplication_id+", id="+id);
            String url = "https://discord.com/api/v8/applications/!app_id/commands/!command_id";
            url=url.replaceAll("!app_id", String.valueOf(gApplication_id)).replaceAll("!command_id", String.valueOf(gID));
            logger.info(fName+".url="+url); logger.info(fName+".token="+gApplication_Token);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            HttpResponse<JsonNode> jsonResponse =a.delete(url)
                    .header("Authorization", "Bot "+gApplication_Token)
                    .header("accept", "application/json")
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+".status ="+jsonResponse.getStatus());
            JSONObject body=new JSONObject();
            try {
                body=jsonResponse.getBody().getObject();
                logger.info(fName+".body ="+body.toString());
                //jsonGet =body;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            if(jsonResponse.getStatus()>299){
                logger.warn(fName+".invalid status");
                logger.error(fName+".body ="+body.toString());
                return false;
            }else{
                logger.info(fName+".got"); gID=0L;gGuild=null;
                jsonDelete=body;
                return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
