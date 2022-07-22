package web.spring.discord;

import io.mokulu.discord.oauth.DiscordAPI;
import io.mokulu.discord.oauth.DiscordOAuth;
import io.mokulu.discord.oauth.model.TokensResponse;
import kong.unirest.json.JSONObject;
import models.lc.helper.lcRandomString;
import models.lsGlobalHelper;
import org.apache.log4j.Logger;
import web.spring.discord.entity.DashUserSession;
import web.spring.iShared;

import java.io.IOException;
import java.util.Arrays;

public interface iDiscord
{
    static DiscordOAuth getDiscordOAuth(String[]scope){
        String fName="[getDiscordOAuth]";
        Logger logger = Logger.getLogger(iDiscord.class);
        logger.info(fName+".init");
        DiscordOAuth oauthHandler = new DiscordOAuth(lsGlobalHelper.sGetGlobal().configfile.getBot().getId(),lsGlobalHelper.sGetGlobal().configfile.getBot().getClientSecret(), lsGlobalHelper.sGetGlobal().configfile.getBot().getRedirectUrl(), scope);
        return oauthHandler;
    }
    static io.mokulu.discord.oauth.model.User getUser(String tokenStr) throws IOException {
        String fName="[getUser]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        DiscordAPI api = new DiscordAPI(tokenStr);
        io.mokulu.discord.oauth.model.User user = api.fetchUser();
        return user;
    }
    static String getRandomToken(){
        String fName="[getRandomToken]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        lcRandomString random=new lcRandomString();
        String token=random.nextString();
        logger.warn(fName+".token="+token+" !WARNING: Should not print it out, remember to hide it!");
        return token;
    }
    static void add2Registry(io.mokulu.discord.oauth.model.User user, TokensResponse tokens, String token) throws Exception {
        String fName="[add2Registry]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        DashUserSession dashUserSession=new DashUserSession();
        dashUserSession.setRandomToken(token).setId(user.getId()).setName(user.getFullUsername())
                .setAccessToken(tokens.getAccessToken()).setRefreshToken(tokens.getRefreshToken())
                .setTokenType(tokens.getTokenType()).setScope(tokens.getScope()).setExpires(tokens.getExpiresIn());
        logger.warn(fName+".dashUserSession="+dashUserSession.toString()+" !WARNING: Should not print it out, remember to hide it!");
        lsGlobalHelper.sGetGlobal().getDashSession().put(token,dashUserSession);
    }
    static void rem4Registry(String token) {
        String fName="[rem4Registry]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        if(!lsGlobalHelper.sGetGlobal().getDashSession().isEmpty()){
            logger.info(fName+".isEmpty");
            return;
        }
        if(!lsGlobalHelper.sGetGlobal().getDashSession().containsKey(token)){
            logger.info(fName+".no such entry to remove");
            return;
        }
        lsGlobalHelper.sGetGlobal().getDashSession().remove(token);
    }
    static boolean isFoundInRegistry(String token) {
        String fName="[isFoundInRegistry]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        if(!lsGlobalHelper.sGetGlobal().getDashSession().isEmpty()){
            logger.info(fName+".isEmpty");
            return false;
        }
        if(!lsGlobalHelper.sGetGlobal().getDashSession().containsKey(token)){
            logger.info(fName+".no such entry to remove");
            return false;
        }
        logger.info(fName+".is in registry");
        return true;
    }
    static DashUserSession get4Registry(String token) {
        String fName="[get4Registry]";
        Logger logger = Logger.getLogger(iShared.class);
        logger.info(fName+".init");
        if(!lsGlobalHelper.sGetGlobal().getDashSession().isEmpty()){
            logger.info(fName+".isEmpty");
            return null;
        }
        if(!lsGlobalHelper.sGetGlobal().getDashSession().containsKey(token)){
            logger.info(fName+".no such entry to remove");
            return null;
        }
        logger.info(fName+".is in registry");
        return lsGlobalHelper.sGetGlobal().getDashSession().get(token);
    }
    final String cookieNameForSession="mfusv";
    interface  KEYS{
        final String random_token="random_token",
        id="id",
        name="name",
        access_token="access_token",
        refresh_token="refresh_token",
        scope="scope",
        expires="expires",
        token_type="token_type";
    }
}