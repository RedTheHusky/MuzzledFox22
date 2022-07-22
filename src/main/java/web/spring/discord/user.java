package web.spring.discord;

import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import io.mokulu.discord.oauth.model.User;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.spring.discord.entity.DashUserSession;
import web.spring.iShared;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

//https://spring.io/guides/tutorials/rest/
//https://www.baeldung.com/exception-handling-for-rest-with-spring
//https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
//https://stackoverflow.com/questions/44839753/returning-json-object-as-response-in-spring-boot
//https://www.baeldung.com/spring-boot-json
//https://www.baeldung.com/spring-mvc-send-json-parameters
//https://knasmueller.net/send-json-objects-via-post-to-spring-boot-controllers
//https://www.geeksforgeeks.org/spring-rest-json-response/
//https://stackoverflow.com/questions/17955777/redirect-to-an-external-url-from-controller-action-in-spring-mvc
@RestController
public class user {
    Logger logger = Logger.getLogger(getClass());
    final String[] scopeDefault ={"identify","guilds"};
    @RequestMapping(value="/discord/auth/url")
    ResponseEntity<Map<String, Object>> getAuthorizationURL(@RequestBody (required=false)Map<String,Object>options, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String fName="[getAuthorizationURL]";
        logger.info(fName+".init");
        try{
            Map<String,Object>map=new HashMap<>();
            String[] scope=scopeDefault;String state=null;
            if(options==null) {
                logger.warn(fName+"no options provided");
            }else{
                logger.warn(fName + "options=" + options.toString());
                if(options.containsKey("scope")){
                    if(!(options.get("scope") instanceof String[])){
                        logger.warn(fName+"invalid type options[scope]");
                        return iShared.respondBackWithFailureMessage_Map("invalid type options[scope]");
                    }else{
                        scope= (String[]) options.get("scope");
                    }
                }
                if(options.containsKey("state")){
                    if(!(options.get("state") instanceof String)){
                        logger.warn(fName+"invalid type options[state]");
                        return iShared.respondBackWithFailureMessage_Map("invalid type options[state]");
                    }else{
                        state= (String) options.get("state");
                    }
                }
            }
            DiscordOAuth oauthHandler = iDiscord.getDiscordOAuth(scope);
            if(oauthHandler==null)throw new Exception("oauthHandler is null!");
            String url=oauthHandler.getAuthorizationURL(state);
            logger.info(fName+"AuthorizationURL="+url);
            map.put("url",url);
            map.put("scope", scope);
            map.put("state",state);
            httpServletRequest.getSession().setAttribute("MY_SESSION_MESSAGES", "ehhh");
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
    @RequestMapping(value="/discord/auth/token",method=RequestMethod.GET)
    ResponseEntity<Map<String, Object>> getAuthorizationTokens(@RequestParam(name="code", required=false) String code,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String fName="[getAuthorizationTokens]";
        logger.info(fName+".init");
        try{
            if(code==null||code.isBlank()){
                logger.warn(fName+" no 'code' provided!");
                return iShared.respondBackWithFailureMessage_Map("no 'code' provided");
            }
            if(httpServletResponse==null)throw new Exception("httpServletResponse is null!");
            DiscordOAuth oauthHandler = iDiscord.getDiscordOAuth(scopeDefault);
            if(oauthHandler==null)throw new Exception("oauthHandler is null!");
            logger.info(fName+"AuthorizationURL="+oauthHandler.getAuthorizationURL(null));
            TokensResponse tokens = oauthHandler.getTokens(code);
            if(tokens==null)throw new Exception("tokens is null!");
            String accessToken="",refreshToken="";
            accessToken = tokens.getAccessToken();
            refreshToken = tokens.getRefreshToken();
            logger.info(fName+"accessToken="+accessToken+", refreshToken="+refreshToken);
            logger.info(fName+"httpServletResponse="+httpServletResponse.getStatus());
            User user=iDiscord.getUser(accessToken);
            if(user==null)throw new Exception("user is null!");
            String randomToken=iDiscord.getRandomToken();
            iDiscord.add2Registry(user,tokens,randomToken);
            logger.info(fName+"user: id="+user.getId()+", name="+user.getFullUsername());
            Cookie cookie=new Cookie(iDiscord.cookieNameForSession,randomToken);
            cookie.setDomain("https://redhusky.hostingerapp.com/");
            httpServletResponse.addCookie(cookie);
            httpServletResponse.setHeader("Location", "https://redhusky.hostingerapp.com/muzzledfox/#");
            httpServletResponse.setStatus(302);
            httpServletRequest.getSession().setAttribute("MY_SESSION_MESSAGES", "ehhh");
            return null;
            //return iShared.respondBackWithSuccessMessage_Map("success");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
    @RequestMapping(value="/discord/auth/refresh",method=RequestMethod.GET)
    ResponseEntity<Map<String, Object>> getAuthorizationRefreshed(@RequestParam(name="token", required=false) String code) {
        String fName="[getAuthorizationTokens]";
        logger.info(fName+".init");
        try{
            DiscordOAuth oauthHandler = iDiscord.getDiscordOAuth(scopeDefault);
            if(oauthHandler==null)throw new Exception("oauthHandler is null!");
            logger.info(fName+"AuthorizationURL="+oauthHandler.getAuthorizationURL(null));
            TokensResponse tokens = oauthHandler.getTokens(code);
            if(tokens==null)throw new Exception("tokens is null!");
            String accessToken="",refreshToken="";
            accessToken = tokens.getAccessToken();
            refreshToken = tokens.getRefreshToken();
            logger.info(fName+"accessToken="+accessToken+", refreshToken="+refreshToken);
            return iShared.respondBackWithSuccessMessage_Map("success");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
    @RequestMapping(value="/discord/auth/user",method=RequestMethod.TRACE)
    ResponseEntity<Map<String, Object>> traceUserAuthorization(@RequestParam(name="token", required=false) String token,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String fName="[traceUserAuthorization]";
        logger.info(fName+".init");
        try{
            if(token==null||token.isBlank()){
                logger.warn(fName+" no 'code' provided!");
                return iShared.respondBackWithFailureMessage_Map("no 'code' provided");
            }
            DashUserSession dashUserSession =iDiscord.get4Registry(token);
            if(dashUserSession==null){
                logger.warn(fName+" no entry found!");
                return iShared.respondBackWithFailureMessage_Map("no entry found!");
            }
            Map<String,Object>map=dashUserSession.getAsMap();
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
    @RequestMapping(value="/discord/auth/user",method=RequestMethod.PUT)
    ResponseEntity<Map<String, Object>> putUserAuthorization(@RequestParam(name=iDiscord.KEYS.random_token, required=false) String random_token,
           @RequestParam(name=iDiscord.KEYS.id, required=false) String id,@RequestParam(name=iDiscord.KEYS.name, required=false) String name,
           @RequestParam(name=iDiscord.KEYS.access_token, required=false) String access_token,@RequestParam(name=iDiscord.KEYS.refresh_token, required=false) String refresh_token,@RequestParam(name=iDiscord.KEYS.token_type, required=false) String token_type,
           @RequestParam(name=iDiscord.KEYS.scope, required=false) String scope, @RequestParam(name=iDiscord.KEYS.expires, required=false) String expires,
           HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String fName="[putUserAuthorization]";
        logger.info(fName+".init");
        try{
            if(access_token==null||access_token.isBlank()||
                    refresh_token==null||refresh_token.isBlank()
            ){
                logger.warn(fName+" no 'code' provided!");
                return iShared.respondBackWithFailureMessage_Map("no 'code' provided");
            }
            if(random_token==null||random_token.isBlank()){
                logger.info(fName+" no random_token provided, will make one");
                random_token=iDiscord.getRandomToken();
            }
            DashUserSession dashUserSession =new DashUserSession();
            dashUserSession.setRandomToken(random_token).setId("");
            if(dashUserSession==null){
                logger.warn(fName+" no entry found!");
                return iShared.respondBackWithFailureMessage_Map("no entry found!");
            }
            Map<String,Object>map=dashUserSession.getAsMap();
            return new ResponseEntity<>(
                    map, new HttpHeaders(), HttpStatus.OK);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }


    ResponseEntity<Map<String, Object>> getAuthorizationTokens3(@RequestParam(name="code", required=false) String code) {
        String fName="[getAuthorizationTokens]";
        logger.info(fName+".init");
        try{
            DiscordOAuth oauthHandler = iDiscord.getDiscordOAuth(scopeDefault);
            if(oauthHandler==null)throw new Exception("oauthHandler is null!");
            logger.info(fName+"AuthorizationURL="+oauthHandler.getAuthorizationURL(null));
            TokensResponse tokens = oauthHandler.getTokens(code);
            if(tokens==null)throw new Exception("tokens is null!");
            String accessToken="",refreshToken="";
            accessToken = tokens.getAccessToken();
            refreshToken = tokens.getRefreshToken();
            logger.info(fName+"accessToken="+accessToken+", refreshToken="+refreshToken);
            return iShared.respondBackWithSuccessMessage_Map("success");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
    ResponseEntity<Map<String, Object>> login2(@RequestParam(name="code", required=false) String code) {
        String fName="[login]";
        logger.info(fName+".init");
        try{
            if(code==null){
                logger.warn(fName+"'code' was not provided!");
                return iShared.respondBackWithFailureMessage_Map("'code' was not provided!");
            }
            logger.info(fName+"code="+code);
            return iShared.respondBackWithSuccessMessage_Map("success");
        }catch (Exception e){
            logger.error(fName + ".exception=" + e+" StackTrace:" + Arrays.toString(e.getStackTrace()));
            return iShared.respondBackWithExceptionMessage_Map(e.getMessage());
        }
    }
}
