package nsfw.chastity.emlalock;

import kong.unirest.HttpRequestWithBody;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.util.Arrays;


public class entityUserEmlalock {
    Logger logger = Logger.getLogger(getClass());
    String cName = "[entityWearer]";
    String keyUser = "user", keychastitysession = "chastitysession";

    public entityUserEmlalock() {
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

    public entityUserEmlalock setProfile(JSONObject jsonObject) {
        String fName = "[setProfile]";
        try {
            if (jsonObject == null) {
                logger.info(fName + "jsonObject is null");
                return null;
            }
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            profile.set(jsonObject);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public entityUserEmlalock setEmlalock4Wearer(JSONObject jsonObject) {
        String fName = "[setEmlalock4Wearer]";
        try {
            if (jsonObject == null) {
                logger.info(fName + "jsonObject is null");
                return null;
            }
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            if (jsonObject.has(keyUser)) {
                user.set(jsonObject.optJSONObject(keyUser));
            }
            if (jsonObject.has(keychastitysession)) {
                session.set(jsonObject.optJSONObject(keychastitysession));
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public entityUserEmlalock setEmlalock4Holder(JSONObject jsonObject) {
        String fName = "[setEmlalock4Holder]";
        try {
            if (jsonObject == null) {
                logger.info(fName + "jsonObject is null");
                return null;
            }
            logger.info(fName + "jsonObject=" + jsonObject.toString());
            if (jsonObject.has(keyUser)) {
                holder.set(jsonObject.optJSONObject(keyUser));
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public JSONObject getProfileJSON() {
        String fName = "[getProfileJSON]";
        try {
            JSONObject jsonObject = new JSONObject();
            logger.info("jsonObject=" + jsonObject.toString());
            jsonObject.put(keyUser, user.getJSON());
            jsonObject.put(keychastitysession, session.getJSON());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }

    public JSONObject getEmlalockWearerJSON() {
        String fName = "[getEmlalockWearerJSON]";
        try {
            JSONObject jsonObject = new JSONObject();
            logger.info("jsonObject=" + jsonObject.toString());
            jsonObject.put(keyUser, user.getJSON());
            jsonObject.put(keychastitysession, session.getJSON());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }

    PROFILE profile = new PROFILE();

    public class PROFILE {
        String cName = "[PROFILE]";
        private JSONObject jsonObject = new JSONObject();

        public PROFILE() {

        }

        String keyUser = "user", fieldApiKey = "api", fieldUserId = "userId";

        public JSONObject getJSON() {
            String fName = "[getJSON]";
            try {
                logger.info(cName + fName + "json=" + jsonObject.toString());
                return jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }

        public PROFILE set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                if (jsonObject == null) {
                    logger.info(cName + fName + "jsonObject is null");
                    return null;
                }
                logger.info(fName + "jsonObject=" + jsonObject.toString());
                this.jsonObject = jsonObject;
                if (jsonObject.has("voting")) {
                    voting.set(jsonObject.getJSONObject("voting"));
                }
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

        private PROFILE createIfNotExists(JSONObject jsonObject, String key, Object object) {
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

        private PROFILE createIfNotExists(String key, Object object) {
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

        private PROFILE putTo(JSONObject jsonObject, String key, Object object) {
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

        private PROFILE putTo(String key, Object object) {
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

        public String getUserId() {
            String fName = "[getUserId]";
            try {
                logger.info(cName + fName);
                return getJSONObject(keyUser).getString(fieldUserId);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public boolean isEmptyUserId() {
            String fName = "[isEmptyUserId]";
            try {
                String str = getUserId();
                logger.info(cName + fName + "str=" + str);
                if (str == null) {
                    logger.info(fName + ".is null>true");
                    return true;
                }
                if (str.isBlank()) {
                    logger.info(fName + ".isBlank>true");
                    return true;
                }
                logger.info(fName + ".default>false");
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }

        public String getApi() {
            String fName = "[getApi]";
            try {
                logger.info(cName + fName);
                return getJSONObject(keyUser).getString(fieldApiKey);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public boolean isEmptyApi() {
            String fName = "[isEmptyApi]";
            try {
                String str = getApi();
                logger.info(cName + fName + "str=" + str);
                if (str == null) {
                    logger.info(fName + ".is null>true");
                    return true;
                }
                if (str.isBlank()) {
                    logger.info(fName + ".isBlank>true");
                    return true;
                }
                logger.info(fName + ".default>false");
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }

        public boolean isEmpty() {
            String fName = "[isEmpty]";
            try {
                if (jsonObject == null) {
                    logger.info(fName + "is null");
                    return true;
                }
                if (jsonObject.isEmpty()) {
                    logger.info(fName + "is Empty");
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

        public PROFILE setUserId(String input) {
            String fName = "[setUserId]";
            try {
                logger.info(cName + fName + "input=" + input);
                if (input == null) input = "";
                String key1 = keyUser;
                createIfNotExists(key1, new JSONObject());
                String key2 = fieldUserId;
                return putTo(getJSONObject(keyUser), key2, input);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public PROFILE setApi(String input) {
            String fName = "[setApi]";
            try {
                logger.info(cName + fName + "input=" + input);
                if (input == null) input = "";
                String key1 = keyUser;
                createIfNotExists(key1, new JSONObject());
                String key2 = fieldApiKey;
                return putTo(getJSONObject(keyUser), key2, input);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        VOTING voting = new VOTING();

        public class VOTING {
            String cName = "[VOTING]";
            private JSONObject jsonObject = new JSONObject();

            public VOTING() {

            }

            String keyVoting = "voting", fieldTimeAdd = "timeAdd", fieldTimeSub = "timeSub", fieldTimeWait = "timeWait", fieldAllowRandom = "allowRandom", fieldAllowAdd = "allowAdd", fieldAllowSub = "allowSub", fieldLogs = "logs", fieldTimeStamp = "timeStamp", fieldEnable = "enable";

            public JSONObject getJSON() {
                String fName = "[getJSON]";
                try {
                    logger.info(cName + fName + "json=" + jsonObject.toString());
                    return jsonObject;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }

            public VOTING set(JSONObject jsonObject) {
                String fName = "[set]";
                try {
                    if (jsonObject == null) {
                        logger.info(cName + fName + "jsonObject is null");
                        return null;
                    }
                    logger.info(fName + "jsonObject=" + jsonObject.toString());
                    this.jsonObject = jsonObject;
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
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
            public boolean getBoolean(String key) {
                String fName = "[getBoolean]";
                try {
                    logger.info(fName + "key=" + key);
                    boolean value = jsonObject.getBoolean(key);
                    logger.info(fName + "value=" + value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }

            public long getTimeWait() {
                String fName = "[getTimeWait]";
                try {
                    return getLong(fieldTimeWait);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public long getTimeAdd() {
                String fName = "[getTimeAdd]";
                try {
                    return getLong(fieldTimeAdd);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public long getTimeSub() {
                String fName = "[getTimeSub]";
                try {
                    return getLong(fieldTimeSub);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public long getTimeStamp() {
                String fName = "[getTimeStamp]";
                try {
                    return getLong(fieldTimeStamp);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public VOTING setTimeWait(long input) {
                String fName = "[setTimeWait]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldTimeWait,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setTimeAdd(long input) {
                String fName = "[setTimeAdd]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldTimeAdd,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setTimeSub(long input) {
                String fName = "[setTimeSub]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldTimeSub,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setTimeStamp(long input) {
                String fName = "[setTimeStamp]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldTimeStamp,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isEnable() {
                String fName = "[isEnable]";
                try {
                    return getBoolean(fieldEnable);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isAllowAdd() {
                String fName = "[isAllowAdd]";
                try {
                    return getBoolean(fieldAllowAdd);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isAllowSub() {
                String fName = "[isAllowSub]";
                try {
                    return getBoolean(fieldAllowSub);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isAllowRandom() {
                String fName = "[isAllowRandom]";
                try {
                    return getBoolean(fieldAllowRandom);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public VOTING setEnable(boolean input) {
                String fName = "[setEnable]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldEnable,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setAllowAdd(boolean input) {
                String fName = "[setAllowAdd]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldAllowAdd,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setAllowSub(boolean input) {
                String fName = "[setAllowSub]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldAllowSub,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setAllowRandom(boolean input) {
                String fName = "[setAllowRandom]";
                try {
                    logger.info(cName+fName + "input=" + input);
                    jsonObject.put(fieldAllowRandom,input);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public boolean isMemberPresentInLogs(Member member) {
                String fName = "[isMemberPresentInLogs]";
                try {
                    return isMemberPresentInLogs(member.getId());
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public boolean isMemberPresentInLogs(String memberId) {
                String fName = "[isMemberPresentInLogs]";
                try {
                    if(!jsonObject.has(fieldLogs)){
                        logger.info(cName+fName+" does not exists");
                        return false;
                    }
                    if(!jsonObject.getJSONObject(fieldLogs).has(memberId)){
                        logger.info(cName+fName+" does not exists");
                        return false;
                    }
                    return true;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            public JSONObject getMember4Logs(Member member) {
                String fName = "[getMember4Logs]";
                try {
                    return getMember4Logs(member.getId());
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            public JSONObject getMember4Logs(String memberId) {
                String fName = "[getMember4Logs]";
                try {
                    if(!isMemberPresentInLogs(memberId)){
                        logger.info(cName+fName+" does not exists");
                        return new JSONObject();
                    }
                    JSONObject value=jsonObject.getJSONObject(fieldLogs).getJSONObject(memberId);
                    logger.info(cName+fName+" value="+value.toString());
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return new JSONObject();
                }
            }
            public long getMemberTimeStamp4Logs(Member member) {
                String fName = "[getMemberTimeStamp4Logs]";
                try {
                    return getMemberTimeStamp4Logs(member.getId());
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public long getMemberTimeStamp4Logs(String memberId) {
                String fName = "[getMemberTimeStamp4Logs]";
                try {
                    long value=getMember4Logs(memberId).getLong(fieldTimeStamp);
                    logger.info(cName+fName+" value="+value);
                    return value;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return 0;
                }
            }
            public VOTING setMember4Logs(Member member,JSONObject jsonObject) {
                String fName = "[setMember4Logs]";
                try {
                    return setMember4Logs(member.getId(),jsonObject);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setMember4Logs(String memberId,JSONObject jsonObject) {
                String fName = "[setMember4Logs]";
                try {
                    if(!this.jsonObject.has(fieldLogs)){
                        this.jsonObject.put(fieldLogs,new JSONObject());
                        logger.info(cName+fName+"creating logs field");
                    }
                    if(jsonObject==null){
                       jsonObject=new JSONObject();
                       logger.info(cName+fName+"jsonObject is null");
                    }
                    this.jsonObject.getJSONObject(fieldLogs).put(memberId,jsonObject);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setMemberTimeStamp4Logs(Member member,long timestamp) {
                String fName = "[setMemberTimeStamp4Logs]";
                try {
                    return setMemberTimeStamp4Logs(member.getId(),timestamp);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }
            public VOTING setMemberTimeStamp4Logs(String memberId,long timestamp) {
                String fName = "[setMemberTimeStamp4Logs]";
                try {
                    if(!jsonObject.has(fieldLogs)){
                        jsonObject.put(fieldLogs,new JSONObject());
                        logger.info(cName+fName+"creating logs field");
                    }
                    if(!jsonObject.getJSONObject(fieldLogs).has(memberId)){
                        jsonObject.getJSONObject(fieldLogs).put(memberId,new JSONObject());
                        logger.info(cName+fName+"creating member field");
                    }
                    jsonObject.getJSONObject(fieldLogs).getJSONObject(memberId).put(fieldTimeStamp,timestamp);
                    return this;
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
            }

        }
    }
    USER user=new USER();
    SESSION session=new SESSION();
    public class USER{
        String cName="[USER]";
        private JSONObject jsonObject=new JSONObject();
        public USER(){

        }
        String keyUserName="username", keyUserId="userid";
        String keywearerrates="wearerrates",keywearerrating="wearerrating", keyholderrates="holderrates",keyholderrating="holderrating",keyageverified="ageverified",keysessions="sessions",keyfailedsessions="failedsessions",keymaxsession="maxsession",keyminsession="minsession",keysumsession="sumsession",keydescription="description",keygender="gender",keychastityrole="chastityrole";
        public JSONObject getJSON(){
            String fName="[getJSON]";
            try {
                logger.info(cName+fName+"json="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public USER set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(cName+fName+"jsonObject is null");
                    return null;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                this.jsonObject=jsonObject;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean hasKey(String key){
            String fName="[hasKey]";
            try {
                logger.info(fName+"key="+key);
                boolean value=jsonObject.has(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getString(String key){
            String fName="[getString]";
            try {
                logger.info(fName+"key="+key);
                String value=jsonObject.getString(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getInt(String key){
            String fName="[getInt]";
            try {
                logger.info(fName+"key="+key);
                int value=jsonObject.getInt(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getLong(String key){
            String fName="[getLong]";
            try {
                logger.info(fName+"key="+key);
                long value=jsonObject.getLong(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getUserName(){
            String fName="[getUserName]";
            try {
                logger.info(cName+fName);
                return getString(keyUserName);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getUserId(){
            String fName="[getUserId]";
            try {
                logger.info(cName+fName);
                return getString(keyUserId);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getGenderAsInt(){
            String fName="[getGenderAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keygender);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public iChastityEmlalock.Genders getGender(){
            String fName="[getGender]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.Genders value=iChastityEmlalock.Genders.valueByCode(getGenderAsInt());
                if(value==null)value=iChastityEmlalock.Genders.NotApplicable;
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return iChastityEmlalock.Genders.NotApplicable;
            }
        }
        public String getGenderAsString(){
            String fName="[getGenderAsString]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.Genders value=getGender();
                return value.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public int getChastityRoleAsInt(){
            String fName="[getChastityRoleAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keychastityrole);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public iChastityEmlalock.Roles getRole(){
            String fName="[getRole]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.Roles value=iChastityEmlalock.Roles.valueByCode(getChastityRoleAsInt());
                if(value==null)value=iChastityEmlalock.Roles.None;
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return iChastityEmlalock.Roles.None;
            }
        }
        public String getRoleAsString(){
            String fName="[getRoleAsString]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.Roles value=getRole();
                return value.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean isValid(){
            String fName="[isValid]";
            logger.info(fName);
            try{
                if(jsonObject==null) {
                    logger.info(fName + ".is null>false");
                    return  false;
                }
                if(jsonObject.isEmpty()) {
                    logger.info(fName + ".is empty>false");
                    return  false;
                }
                logger.info(fName + ".has>true");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return false;
            }
        }
        public boolean isEmpty(){
            String fName="[isEmpty]";
            try {
                if(jsonObject==null){
                    logger.info(fName+"is null");
                    return true;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"is Empty");
                    return true;
                }
                logger.info(fName+"none above");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        private String profilepicture="";
        public boolean isProfilePicBlank(){
            String fName="[isProfilePicBlank]";
            try {
                logger.info(cName+fName);
                if(profilepicture==null||profilepicture.isBlank()){
                    logger.info(cName+fName + "attempt to get");
                    getProfilePic();
                }
                if(profilepicture==null){
                    logger.info(cName+fName + "isNull");
                    return  true;
                }
                if(profilepicture.isBlank()){
                    logger.info(cName+fName + "isBlank");
                    return  true;
                }
                logger.info(cName+fName + "default");
                return  false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public String getProfilePic(){
            String fName="[getProfilePic]";
            try {
                logger.info(cName+fName);
                if(profilepicture!=null&&!profilepicture.isBlank()){
                    logger.info(cName+fName + "already has>skip");
                    return  profilepicture;
                }
                String url=iChastityEmlalock.gURLUserProfile;
                url=url.replaceAll("!id",getUserId());
                logger.info(cName+fName + "url="+url);
                profilepicture=getUrlAsText(url);
                return profilepicture;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getProfilePicUrl(){
            String fName="[getProfilePic]";
            try {
                if(isProfilePicBlank()){
                    logger.info(cName+fName + "isblank");
                    return  "";
                }
                logger.info(cName+fName);
                String url=iChastityEmlalock.gUrlProfileFile.replaceAll("!id",getProfilePic());
                logger.info(cName+fName + "url="+url);
                return url;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public int getSessionsCount(){
            String fName="[getSessionsCount]";
            try {
                logger.info(cName+fName);
                return getInt(keysessions);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getMaxSessionDuration(){
            String fName="[getMaxSessionDuration]";
            try {
                logger.info(cName+fName);
                return getLong(keymaxsession);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getMinSessionDuration(){
            String fName="[getMinSessionDuration]";
            try {
                logger.info(cName+fName);
                return getLong(keyminsession);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getSumSessionDuration(){
            String fName="[getSumSessionDuration]";
            try {
                logger.info(cName+fName);
                return getLong(keysumsession);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
    }
    public  class SESSION{
        String cName="[SESSION]";
        private JSONObject jsonObject=new JSONObject();
        public SESSION(){

        }
        String keychastitysessionid="chastitysessionid",keycreatorid="creatorid",keywearerid="wearerid",keyholderid="holderid",keystatus="status",keysessiontype="sessiontype";
        String keyfriendlink="friendlink",keyreqlink="reqlink",keyrequirements="requirements",keyfriendlinkadd="friendlinkadd",keyfriendlinksub="friendlinksub",keyreqlinkadd="reqlinkadd",keyreqlinksub="reqlinksub",keyfriendlinkid="friendlinkid",keyreqlinkid="reqlinkid";
        String keydurationtype="durationtype",keyminduration="minduration",keymaxduration="maxduration",keystartduration="startduration",keyduration="duration";
        String keyinterval="interval",keylastverification="lastverification";
        String keyCode="code",keyFile="file";
        String keystartdate="startdate",keyenddate="enddate";
        String gUrlPage2Profile="https://www.emlalock.com/#/profile/!id";
        String keyhygieneopening="hygieneopening",keyincleaning="incleaning", keycleaningaction="cleaningaction",keycleaningpenalty="cleaningpenalty",keycleaningperiod="cleaningperiod",keycleanings="cleanings",keycleaningsperday="cleaningsperday",keycleaningstarted="cleaningstarted",keytimeforcleaning="timeforcleaning";
        public JSONObject getJSON(){
            String fName="[getJSON]";
            try {
                logger.info(cName+fName+"json="+jsonObject.toString());
                return jsonObject;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public SESSION set(JSONObject jsonObject){
            String fName="[set]";
            try {
                if(jsonObject==null){
                    logger.info(cName+fName+"jsonObject is null");
                    return null;
                }
                logger.info(fName+"jsonObject="+jsonObject.toString());
                this.jsonObject=jsonObject;
                return this;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean hasKey(String key){
            String fName="[hasKey]";
            try {
                logger.info(fName+"key="+key);
                boolean value=jsonObject.has(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getString(String key){
            String fName="[getString]";
            try {
                logger.info(fName+"key="+key);
                String value=jsonObject.getString(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getInt(String key){
            String fName="[getInt]";
            try {
                logger.info(fName+"key="+key);
                int value=jsonObject.getInt(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getLong(String key){
            String fName="[getLong]";
            try {
                logger.info(fName+"key="+key);
                long value=jsonObject.getLong(key);
                logger.info(fName+"value="+value);
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isValid(){
            String fName="[isValid]";
            logger.info(fName);
            try{
                if(jsonObject==null) {
                    logger.info(fName + ".is null>false");
                    return  false;
                }
                if(jsonObject.isEmpty()) {
                    logger.info(fName + ".is empty>false");
                    return  false;
                }
                logger.info(fName + ".has>true");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return false;
            }
        }
        public String getHolderId(){
            String fName="[getKeyHolderId]";
            try {
                logger.info(cName+fName);
                return getString(keyholderid);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getWearerId(){
            String fName="[getWearerId]";
            try {
                logger.info(cName+fName);
                return getString(keywearerid);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getSessionId(){
            String fName="[getSessionId]";
            try {
                logger.info(cName+fName);
                return getString(keychastitysessionid);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean hasKeyHolder(){
            String fName="[hasKeyHolder]";
            logger.info(fName);
            try{
                if(!jsonObject.has(keyholderid)) {
                    logger.info(fName + ".has no holder id>false");
                    return  false;
                }
                if(jsonObject.getString(keyholderid).isBlank()) {
                    logger.info(fName + ".holder id is blank>false");
                    return  false;
                }
                logger.info(fName + ".has>true");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return false;
            }
        }
        public boolean isOwned(){
            String fName="[isOwned]";
            logger.info(fName);
            try{
                if(!isValid()){
                    logger.info(fName + ".not valid>false");
                    return  false;
                }
                if(!hasKeyHolder()) {
                    logger.info(fName + ".has no holder id>false");
                    return  false;
                }
                if(getHolderId().equalsIgnoreCase(user.getUserId())){
                    logger.info(fName + ".same id>false");
                    return  false;
                }
                logger.info(fName + ".is owned>true");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getSessionTypeAsInt(){
            String fName="[getSessionTypeAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keysessiontype);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public iChastityEmlalock.SessionTypes getSessionType(){
            String fName="[getSessionType]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.SessionTypes value=iChastityEmlalock.SessionTypes.valueByCode(getSessionTypeAsInt());
                if(value==null)value=iChastityEmlalock.SessionTypes.None;
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return iChastityEmlalock.SessionTypes.None;
            }
        }
        public String getSessionTypeAsString(){
            String fName="[getSessionTypeAsString]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.SessionTypes value=getSessionType();
                return value.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public int getDurationTypeAsInt(){
            String fName="[getDurationTypeAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keydurationtype);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public iChastityEmlalock.DurationTypes getDurationType(){
            String fName="[getDurationType]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.DurationTypes value=iChastityEmlalock.DurationTypes.valueByCode(getDurationTypeAsInt());
                if(value==null)value=iChastityEmlalock.DurationTypes.None;
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return iChastityEmlalock.DurationTypes.None;
            }
        }
        public String getDurationTypeAsString(){
            String fName="[getDurationTypeAsString]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.DurationTypes value=getDurationType();
                return value.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public long getStartingDurationAsLong(){
            String fName="[getStartingDurationAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keystartduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getStarDurationAsLong(){
            String fName="[getStartDurationAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keystartduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getMaxDurationAsLong(){
            String fName="[getMaxDurationAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keymaxduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getMinDurationAsLong(){
            String fName="[getMinDurationAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keyminduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getDurationAsLong(){
            String fName="[getDurationAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keyduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getStartDateAsLong(){
            String fName="[getStartDateAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keystartdate);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public long getEndDateAsLong(){
            String fName="[getEndDateAsLong]";
            try {
                logger.info(cName+fName);
                return getLong(keyenddate);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean hasStartDuration(){
            String fName="[hasStartDuration]";
            try {
                logger.info(cName+fName);
                return hasKey(keystartduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasMaxDuration(){
            String fName="[hasMaxDuration]";
            try {
                logger.info(cName+fName);
                return hasKey(keymaxduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasMinDuration(){
            String fName="[hasMinDuration]";
            try {
                logger.info(cName+fName);
                return hasKey(keyminduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasDuration(){
            String fName="[hasDuration]";
            try {
                logger.info(cName+fName);
                return hasKey(keyduration);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasStartDate(){
            String fName="[hasStartDate]";
            try {
                logger.info(cName+fName);
                return hasKey(keystartdate);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasEndDate(){
            String fName="[hasEndDate]";
            try {
                logger.info(cName+fName);
                return hasKey(keyenddate);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean hasHygieneOpening(){
            String fName="[hasHygieneOpening]";
            try {
                logger.info(cName+fName);
                return hasKey(keyhygieneopening);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getHygieneOpeningAsInt(){
            String fName="[getHygieneOpeningAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keyhygieneopening);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean hasRequirements(){
            String fName="[hasRequirements]";
            try {
                logger.info(cName+fName);
                return hasKey(keyrequirements);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getRequirementsAsInt(){
            String fName="[getRequirementsAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keyrequirements);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getRequirementsAsStr(){
            String fName="[getRequirementsAsStr]";
            try {
                logger.info(cName+fName);
                return String.valueOf(getInt(keyrequirements));
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean hasCleaningsPerDay(){
            String fName="[hasCleaningsPerDay]";
            try {
                logger.info(cName+fName);
                return hasKey(keycleaningsperday);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getCleaningsPerDay(){
            String fName="[getCleaningsPerDay]";
            try {
                logger.info(cName+fName);
                return getInt(keycleaningsperday);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean hasCleaningPeriod(){
            String fName="[hasCleaningPeriod]";
            try {
                logger.info(cName+fName);
                return hasKey(keycleaningperiod);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getCleaningPeriodAsInt(){
            String fName="[getCleaningPeriodAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keycleaningperiod);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public iChastityEmlalock.CleaningPeriodType getCleaningPeriod(){
            String fName="[getCleaningPeriod]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.CleaningPeriodType value=iChastityEmlalock.CleaningPeriodType.valueByCode(getCleaningPeriodAsInt());
                if(value==null)value=iChastityEmlalock.CleaningPeriodType.None;
                return value;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return iChastityEmlalock.CleaningPeriodType.None;
            }
        }
        public String getCleaningPeriodAsString(){
            String fName="[getCleaningPeriodAsString]";
            try {
                logger.info(cName+fName);
                iChastityEmlalock.CleaningPeriodType value=getCleaningPeriod();
                return value.getName();
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public boolean hasTimeforCleaning(){
            String fName="[hasTimeforCleaning]";
            try {
                logger.info(cName+fName);
                return hasKey(keytimeforcleaning);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getTimeforCleaningAsInt(){
            String fName="[getTimeforCleaningAsInt]";
            try {
                logger.info(cName+fName);
                return getInt(keytimeforcleaning);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isEmpty(){
            String fName="[isEmpty]";
            try {
                if(jsonObject==null){
                    logger.info(fName+"is null");
                    return true;
                }
                if(jsonObject.isEmpty()){
                    logger.info(fName+"is Empty");
                    return true;
                }
                logger.info(fName+"none above");
                return false;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isInSession(){
            String fName="[isInSession]";
            logger.info(fName);
            try{
                if(jsonObject==null) {
                    logger.info(fName + ".is null>false");
                    return  false;
                }
                if(jsonObject.isEmpty()) {
                    logger.info(fName + ".is empty>false");
                    return  false;
                }
                logger.info(fName + ".has>true");
                return  true;
            } catch (Exception e) {
                logger.error(fName+".exception=" + e);
                logger.error(cName +fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
                //lsMessageHelper.lsSendQuickErrorEmbedMessageResponse(gTextChannel, gUser,gTitle, e.toString());
                return false;
            }
        }
        private JSONObject sessionpicture =new JSONObject();
        public JSONObject getSessionPicture(){
            String fName="[getSessionPicture]";
            try {
                logger.info(cName+fName);
                if(sessionpicture !=null&&!sessionpicture.isEmpty()){
                    logger.info(cName+fName + "already has>skip");
                    return sessionpicture;
                }
                if(getSessionTypeAsInt()!=2){
                    logger.info(cName+fName + "invalid type");
                    return null;
                }
                String url=iChastityEmlalock.gUrlSessionPicture;
                url=url.replaceAll("!id",getSessionId());
                logger.info(cName+fName + "url="+url);
                sessionpicture =getUrlAsJson(url);
                if(sessionpicture==null)sessionpicture=new JSONObject();
                return sessionpicture;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getSessionPictureFileName(){
            String fName="[getSessionPictureFileName]";
            try {
                logger.info(cName+fName);
                if(sessionpicture ==null||sessionpicture.isEmpty()){
                    logger.info(cName+fName + "get");
                    getSessionPicture();
                }
                return sessionpicture.getString("file");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getSessionPictureCode(){
            String fName="[getSessionPicture]";
            try {
                logger.info(cName+fName);
                if(sessionpicture ==null||sessionpicture.isEmpty()){
                    logger.info(cName+fName + "get");
                    getSessionPicture();
                }
                return sessionpicture.getString("code");
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean hasInterval(){
            String fName="[hasInterval]";
            try {
                logger.info(cName+fName);
                return hasKey(keyinterval);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public long getInterval(){
            String fName="[getInterval]";
            try {
                logger.info(cName+fName);
                return getLong(keyinterval);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean hasLastVerification(){
            String fName="[hasLastVerification]";
            try {
                logger.info(cName+fName);
                return hasKey(keylastverification);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public long getLastVerification(){
            String fName="[getLastVerification]";
            try {
                logger.info(cName+fName);
                return getLong(keylastverification);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }


    }
    USER holder=new USER();
    public entityUserEmlalock setSessionId(String sessionId){
        String fName="[setSessionId]";
        try {
            logger.info(fName+"sessionId="+sessionId);
            if(sessionId==null)sessionId="";
            this.sessionId=sessionId;
            return this;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getSessionId(){
        String fName="[getSessionId]";
        try {
            logger.info(fName+"value="+sessionId);
            return sessionId;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    String sessionId="";
    private JSONObject getUrlAsJson(String url) {
        String fName = "[getUrlAsJson]";
        logger.info(fName);
        try {
            logger.info(fName + "url="+url);
            Unirest a= new Unirest();
            HttpRequestWithBody b; HttpResponse<JsonNode> jsonResponse;JsonNode body;
            //b=a.get(url).header("sessionid",gSessionId);
            //jsonResponse=b.asJson();
            jsonResponse=a.get(url).header("sessionid",sessionId).asJson();
            if(jsonResponse.getStatus()>299){
                logger.error(fName+".invalid status");
                return null;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body.toString());
            if(body.isArray()){
                logger.error(fName+".body is an array");
                return null;
            }
            JSONObject objects=new JSONObject();
            if(!body.getObject().isEmpty()){
                objects =body.getObject();
            }
            if(objects.isEmpty()){
                logger.error(fName+".is empty");
                return new JSONObject();
            }
            return objects;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    private String getUrlAsText(String url) {
        String fName = "[getUrlAsJson]";
        logger.info(fName);
        try {
            logger.info(fName + "url="+url);
            Unirest a= new Unirest();
            HttpRequestWithBody b; HttpResponse<String> jsonResponse;String body;
            //b=a.get(url).header("sessionid",gSessionId);
            //jsonResponse=b.asJson();
            jsonResponse=a.get(url).header("sessionid",sessionId).asString();
            if(jsonResponse.getStatus()>299){
                logger.error(fName+".invalid status");
                return null;
            }
            body=jsonResponse.getBody();
            logger.info(fName+".body ="+body);
            return body;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
