package util.inhouse.ageverified;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Member;
import nsfw.chastity.emlalock.entityUserEmlalock;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class entityAgeVerifyGuild {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[entityWearer]";
    String keyUser = "user", keychastitysession = "chastitysession";

    public  interface KEYS{
        String guilds= llCommonKeys.keyGuilds;
        String id= llCommonKeys.keyId;
        public  interface  GUILD{
            String roles= llCommonKeys.keyRoles;
        }
    }
    public entityAgeVerifyGuild() {
        String fName = "[constructor]";
        logger.info(fName);
    }

    public boolean clear() {
        String fName = "[clear]";
        try {

            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    long guildId=0;
    public entityAgeVerifyGuild set(JSONObject jsonObject) {
        String fName = "[set]";
        try {
            if (jsonObject == null) {
                logger.info(fName + "jsonObject is null");
                return null;
            }
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            String key="";
            key=KEYS.guilds; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                JSONArray jsonArray=jsonObject.optJSONArray(key);
                for(int i=0;i<jsonArray.length();i++){
                    guildentryList.add(new GUILDENTRY(jsonArray.getJSONObject(i)));
                }
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJSON() {
        String fName = "[getEmlalockWearerJSON]";
        try {
            JSONObject jsonObject = new JSONObject();
            logger.info("jsonObject=" + jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }

    GUILDENTRY guildEntry = new GUILDENTRY();
    List<GUILDENTRY> guildentryList=new ArrayList<>();
    public class GUILDENTRY {
        String cName = "[PROFILE]";
        String id="";

        public GUILDENTRY() {

        }
        public GUILDENTRY(JSONObject jsonObject){
            set(jsonObject);
        }
        public JSONObject getJSON() {
            String fName = "[getJSON]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(KEYS.id,getId());
                jsonObject.put(KEYS.GUILD.roles,getRolesJson());
                logger.info(cName + fName + "json=" + jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }

        public GUILDENTRY set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if (jsonObject == null) {
                    logger.info(cName + fName + "jsonObject is null");
                    return null;
                }
                logger.info(fName + "jsonObject=" + jsonObject.toString());
                String key="";
                key=KEYS.id; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    id=jsonObject.optString(key,"");
                }
                key=KEYS.GUILD.roles; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                    JSONArray jsonArray=jsonObject.optJSONArray(key);
                    for(int i=0;i<jsonArray.length();i++){
                        roles.add(new ROLEENTRY(jsonArray.getJSONObject(i)));
                    }
                }
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName = "[getId]";
            try {
                return id;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdLong() {
            String fName = "[getIdLong]";
            try {
                return Long.parseLong(getId());
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public class ROLEENTRY {
            String cName = "[ROLEENTRY]";
            public ROLEENTRY() {

            }
            public ROLEENTRY(JSONObject jsonObject){
                set(jsonObject);
            }
            public JSONObject getJSON() {
                String fName = "[getJSON]";
                try {
                    JSONObject jsonObject=new JSONObject();
                    logger.info(cName + fName + "json=" + jsonObject.toString());
                    return jsonObject;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }

            public ROLEENTRY set(JSONObject jsonObject) {
                String fName = "[set]";
                try {
                    if (jsonObject == null) {
                        logger.info(cName + fName + "jsonObject is null");
                        return null;
                    }
                    logger.info(fName + "jsonObject=" + jsonObject.toString());
                    String key="";
                    key=KEYS.id; if(jsonObject.has(key)&&!jsonObject.isNull(key)){
                        id=jsonObject.optString(key,"");
                    }
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            String id="";
            public String getId() {
                String fName = "[getId]";
                try {
                    return id;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public long getIdLong() {
                String fName = "[getIdLong]";
                try {
                    return Long.parseLong(getId());
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }

        }
        List<ROLEENTRY>roles=new ArrayList<>();
        public boolean isRolesEmpty() {
            String fName = "[isEmpty]";
            try {
                if (roles == null) {
                    logger.info(fName + "json is null");
                    return true;
                }
                if (roles.isEmpty()) {
                    logger.info(fName + "json is Empty");
                    return true;
                }
                logger.info(fName + "none above");
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public JSONArray getRolesJson() {
            String fName = "[getRolesJson]";
            try {
                JSONArray jsonArray=new JSONArray();
                if (isRolesEmpty()) {
                    logger.info(fName + "isRolesEmpty");
                    return new JSONArray();
                }
                for(ROLEENTRY role:roles){
                    jsonArray.put(role.getJSON());
                }
                return jsonArray;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<ROLEENTRY> getRolesList() {
            String fName = "[getRolesList]";
            try {
                if (isRolesEmpty()) {
                    logger.info(fName + "isRolesEmpty");
                    return new ArrayList<>();
                }
                return roles;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ROLEENTRY getRoleAt(int index) {
            String fName = "[getRoleAt]";
            try {
                logger.info(fName + "index="+index);
                if (isRolesEmpty()) {
                    logger.info(fName + "isRolesEmpty");
                    return null;
                }
                return roles.get(index);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public ROLEENTRY getRoleById(long id) {
            String fName = "[getRoleById]";
            try {
                logger.info(fName + "id="+id);
                if (isRolesEmpty()) {
                    logger.info(fName + "isRolesEmpty");
                    return null;
                }
                for(ROLEENTRY role:roles){
                    if(role.getIdLong()==id){
                        logger.info(fName + "found");
                        return role;
                    }
                }
                logger.info(fName + "none");
                return null;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public boolean isGuildsEmpty() {
        String fName = "[isGuildsEmpty]";
        try {
            if (guildentryList == null) {
                logger.info(fName + "json is null");
                return true;
            }
            if (guildentryList.isEmpty()) {
                logger.info(fName + "json is Empty");
                return true;
            }
            logger.info(fName + "none above");
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public JSONArray getGuildJson() {
        String fName = "[getGuildsJson]";
        try {
            JSONArray jsonArray=new JSONArray();
            if (isGuildsEmpty()) {
                logger.info(fName + "isRolesEmpty");
                return new JSONArray();
            }
            for(GUILDENTRY role:guildentryList){
                jsonArray.put(role.getJSON());
            }
            return jsonArray;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<GUILDENTRY> getGuildList() {
        String fName = "[getGuildsList]";
        try {
            if (isGuildsEmpty()) {
                logger.info(fName + "isRolesEmpty");
                return new ArrayList<>();
            }
            return guildentryList;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public GUILDENTRY getGuildAt(int index) {
        String fName = "[getGuildAt]";
        try {
            logger.info(fName + "index="+index);
            if (isGuildsEmpty()) {
                logger.info(fName + "isRolesEmpty");
                return null;
            }
            return guildentryList.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public GUILDENTRY getGuildById(long id) {
        String fName = "[getGuildById]";
        try {
            logger.info(fName + "id="+id);
            if (isGuildsEmpty()) {
                logger.info(fName + "isRolesEmpty");
                return null;
            }
            for(GUILDENTRY role:guildentryList){
                if(role.getIdLong()==id){
                    logger.info(fName + "found");
                    return role;
                }
            }
            logger.info(fName + "none");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


}
