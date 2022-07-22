package web.spring;

import io.mokulu.discord.oauth.DiscordAPI;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import kong.unirest.json.JSONObject;
import models.lc.helper.lcRandomString;
import models.lsGlobalHelper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public interface iShared
{
    static ResponseEntity<Map<String,Object>> respondBackWithExceptionMessage_Map(String str){
        String fName="[respondBackWithExceptionMessage_Map]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        Map<String,Object>map=new HashMap<>();
        map.put("message",str);
        return new ResponseEntity<>(
                map, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    static ResponseEntity<Map<String,Object>> respondBackWithSuccessMessage_Map(String str){
        String fName="[respondBackWithSuccessMessage_Map]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        Map<String,Object>map=new HashMap<>();
        map.put("message",str);
        return new ResponseEntity<>(
                map, new HttpHeaders(), HttpStatus.OK);
    }
    static ResponseEntity<Map<String,Object>> respondBackWithFailureMessage_Map(String str){
        String fName="[respondBackWithFailure_Map]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        Map<String,Object>map=new HashMap<>();
        map.put("message",str);
        return new ResponseEntity<>(
                map, new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
    }

}