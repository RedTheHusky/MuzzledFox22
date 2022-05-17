package nsfw.faprulette;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import nsfw.chastity.emlalock.iChastityEmlalock;
import org.apache.log4j.Logger;

import java.util.Arrays;


public class entityUser {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[entityUser]";


    public entityUser() {
        String fName = "[constructor]";
        logger.info(fName);
    }

    public boolean clear() {
        String fName = "[clear]";
        try {
            jsonObject=new JSONObject();
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    JSONObject jsonObject=new JSONObject();
    public entityUser set(JSONObject jsonObject) {
        String fName = "[setProfile]";
        try {
            if (jsonObject == null) {
                logger.info(fName + "jsonObject is null");
                return null;
            }
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            this.jsonObject=jsonObject;
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJSON() {
        String fName = "[getJSON]";
        try {
            logger.info("jsonObject=" + jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }


    public boolean hasKey(String key) {
        String fName = "[hasKey]";
        try {
            logger.info(fName + "key=" + key);
            boolean value = jsonObject.has(key);
            logger.info(fName + "value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private entityUser createIfNotExists(JSONObject jsonObject, String key, Object object) {
        String fName = "[createIfNotExists]";
        try {
            logger.info(fName + "key=" + key);
            if (hasKey(key)) {
                return null;
            }
            jsonObject.put(key, object);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private entityUser createIfNotExists(String key, Object object) {
        String fName = "[createIfNotExists]";
        try {
            logger.info(fName + "key=" + key);
            if (hasKey(key)) {
                return null;
            }
            jsonObject.put(key, object);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private entityUser putTo(JSONObject jsonObject, String key, Object object) {
        String fName = "[putTo]";
        try {
            logger.info(fName + "key=" + key);
            jsonObject.put(key, object);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private entityUser putTo(String key, Object object) {
        String fName = "[putTo]";
        try {
            logger.info(fName + "key=" + key);
            jsonObject.put(key, object);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getJSONObject(String key) {
        String fName = "[getJSONObject]";
        try {
            logger.info(fName + "key=" + key);
            JSONObject value = jsonObject.getJSONObject(key);
            logger.info(fName + "value=" + value.toString());
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getString(String key) {
        String fName = "[getString]";
        try {
            logger.info(fName + "key=" + key);
            String value = jsonObject.getString(key);
            logger.info(fName + "value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getInt(String key) {
        String fName = "[getInt]";
        try {
            logger.info(fName + "key=" + key);
            int value = jsonObject.getInt(key);
            logger.info(fName + "value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public long getLong(String key) {
        String fName = "[getLong]";
        try {
            logger.info(fName + "key=" + key);
            long value = jsonObject.getLong(key);
            logger.info(fName + "value=" + value);
            return value;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }

}
