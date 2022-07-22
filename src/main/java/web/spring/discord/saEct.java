package web.spring.discord;
import models.lsGlobalHelper;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.spring.iShared;

import java.util.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

//https://spring.io/guides/tutorials/rest/
//https://www.baeldung.com/exception-handling-for-rest-with-spring
//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
//https://stackoverflow.com/questions/44839753/returning-json-object-as-response-in-spring-boot
//https://www.baeldung.com/spring-boot-json
//https://www.baeldung.com/spring-mvc-send-json-parameters
//https://knasmueller.net/send-json-objects-via-post-to-spring-boot-controllers
//https://www.geeksforgeeks.org/spring-rest-json-response/
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@RestController
public class saEct {
    Logger logger = Logger.getLogger(getClass());

    //@RequestMapping(value="/guilds", method = RequestMethod.GET)
    @GetMapping("/discord/guilds")
    ResponseEntity<Map<String, Object>> getGuilds(@RequestBody (required=false)Map<String,Object>options) {
        String fName="[getGuilds]";
        logger.info(fName+".init");
        try{
            int optionListLimit=0,optionListStartingPoint=0;
            boolean optionCount=false;
            boolean optionDisplay=false,optionDisplayName=false,optionDisplayMembers=false,optionDisplayTextChannels=false,optionDisplayDescription=false,optionDisplayIconUrl=false,optionDisplayBannerUrl=false,optionDisplaySplasUrl=false,optionDisplayVoiceChannels=false;
            if(options==null) {
                logger.warn(fName+"no options provided");
            }else{
                logger.warn(fName+"options="+ options.toString());
                if(options.containsKey("count")) {
                    if(!(options.get("count") instanceof Boolean)){
                        logger.warn(fName+"invalid type options[count]");
                        return iShared.respondBackWithFailureMessage_Map("invalid type options[count]");
                    }else{
                        optionCount= (Boolean) options.get("count");
                    }
                }
                if(options.containsKey("limit")){
                    if(!(options.get("limit") instanceof Integer)){
                        logger.warn(fName+"invalid type options[limit]");
                        return iShared.respondBackWithFailureMessage_Map("invalid type options[limit]");
                    }else{
                        optionListLimit= (Integer) options.get("limit");
                    }
                }
                if(options.containsKey("offset")){
                    if(!(options.get("offset") instanceof Integer)){
                        logger.warn(fName+"invalid type options[offset]");
                        return iShared.respondBackWithFailureMessage_Map("invalid type options[offset]");
                    }else{
                        optionListStartingPoint= (Integer) options.get("offset");
                    }
                }
                if(options.containsKey("display")) {
                    optionDisplay=true;
                    List<Object>display= (List<Object>) options.get("display");
                    if(display.contains("name")) optionDisplayName=true;
                    if(display.contains("description")) optionDisplayDescription=true;
                    if(display.contains("icon_url")) optionDisplayIconUrl=true;
                    if(display.contains("banner_url")) optionDisplayBannerUrl=true;
                    if(display.contains("splash_url")) optionDisplaySplasUrl=true;
                    if(display.contains("member_count")) optionDisplayMembers=true;
                    if(display.contains("text_channel_count")) optionDisplayTextChannels=true;
                    if(display.contains("voice_channel_count")) optionDisplayVoiceChannels=true;
                }
            }
            List<Guild>guilds=lsGlobalHelper.sGetGlobal().getGuildList();
            int size=guilds.size();
            if(optionListLimit>size||optionListLimit<=0){
                optionListLimit=size;
            }
            if(optionListStartingPoint>=size||optionListStartingPoint<0){
                optionListStartingPoint=0;
            }
            logger.warn(fName+"global.guilds.size="+size+", optionListStartingPoint="+ optionListStartingPoint+", optionListLimit="+optionListLimit);
            Map<String, Object> map = new HashMap<>();
            List<Object>guilds_str=new ArrayList<>();
            /*for(Guild guild:lsGlobalHelper.sGetGlobal().getGuildList()){
                guilds.add(guild.getId());
            }*/
            while(optionListStartingPoint<optionListLimit){
                Guild guild=guilds.get(optionListStartingPoint);
                if(!optionDisplay)guilds_str.add(guild.getIdLong());
                else{
                    Map<String, Object> guild_entity = new HashMap<>();
                    guild_entity.put("id",guild.getIdLong());
                    if(optionDisplayName) guild_entity.put("name",guild.getName());
                    if(optionDisplayDescription) guild_entity.put("description",guild.getDescription());
                    if(optionDisplayIconUrl) guild_entity.put("icon_url",guild.getIconUrl());
                    if(optionDisplayBannerUrl) guild_entity.put("banner_url",guild.getBannerUrl());
                    if(optionDisplaySplasUrl) guild_entity.put("splash_url",guild.getSplashUrl());
                    if(optionDisplayMembers) guild_entity.put("member_count",guild.getMemberCount());
                    if(optionDisplayTextChannels) guild_entity.put("text_channel_count",guild.getTextChannels().size());
                    if(optionDisplayVoiceChannels) guild_entity.put("voice_channel_count",guild.getVoiceChannels().size());
                    guilds_str.add(guild_entity);
                }
                optionListStartingPoint++;
            }
            if(optionCount) map.put("guilds",guilds_str.size());
            else map.put("guilds",guilds_str);
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            /*throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Foo Not Found", e);*/
            /*return new ResponseEntity<>(
                    (Map<String, Object>) new HashMap<String, Object>().put("message",e.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);*/
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
    /*class entityGuild{
        //@NotNull
        //@Min(11)
        Object guild_id;
    }*/
    @GetMapping("/discord/guild")
    ResponseEntity<Map<String, Object>> getGuild(@RequestBody Map<String,Object>input) {
        String fName="[getGuilds]";
        logger.info(fName+".init");
        try{
            logger.warn(fName+"input="+ input.toString());
            if(input==null) {
                logger.warn(fName+"no input provided");
                return iShared.respondBackWithFailureMessage_Map("no input provided");
            }
            if(!input.containsKey("guild_id")){
                logger.warn(fName+"no input{guild_id} provided");
                return iShared.respondBackWithFailureMessage_Map("no input{guild_id} provided");
            }
            Object guild_id=input.get("guild_id");Guild guild;
            if(guild_id instanceof String){
                guild=lsGlobalHelper.sGetGlobal().getGuild((String) guild_id);
            }else
            if(guild_id instanceof Integer){
                guild=lsGlobalHelper.sGetGlobal().getGuild((Integer) guild_id);
            }else{
                logger.warn(fName+"input{guild_id} is invalid type");
                return new ResponseEntity<>(
                        (Map<String, Object>) new HashMap<String, Object>().put("message","invalid type input{guild_id}"), new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
            }
            if(guild==null){
                logger.warn(fName+"no guild");
                return iShared.respondBackWithFailureMessage_Map("no guild with id "+guild_id);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id",guild.getIdLong());
            if(input.containsKey("get")){
                List<Object>options= (List<Object>) input.get("get");
                if(options.contains("name")) map.put("name",guild.getName());
                if(options.contains("description")) map.put("description",guild.getDescription());
                if(options.contains("icon_url")) map.put("icon_url",guild.getIconUrl());
                if(options.contains("banner_url")) map.put("banner_url",guild.getBannerUrl());
                if(options.contains("splash_url")) map.put("splash_url",guild.getSplashUrl());
                if(options.contains("member_count")) map.put("member_count",guild.getMemberCount());
                if(options.contains("text_channel_count")) map.put("text_channel_count",guild.getTextChannels().size());
                if(options.contains("voice_channel_count")) map.put("voice_channel_count",guild.getVoiceChannels().size());
            }
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }

}
