package web.jexpress;

import org.apache.log4j.Logger;

import java.util.*;

public interface webHelper1 {
    static boolean hasKeyParameter(Map<String, List<String>> parameters, String key) {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[hasKeyParameter]";
        try {
            logger.info(fName+"key="+key+", parameters="+parameters.toString());
            boolean hasKey=false;String key2="";
            if(parameters.containsKey(key)){
                logger.info(fName + "has true 1");
                hasKey=true;
            }
            if(!hasKey&&parameters.containsKey(key.toLowerCase())){
                logger.info(fName + "has true 2");
                hasKey=true;
            }
            if(!hasKey){
                Set<String> keys=parameters.keySet();
                Iterator<String>keysIterator=keys.iterator();
                while(!hasKey&&keysIterator.hasNext()){
                    key2=keysIterator.next();
                    if(key2.equalsIgnoreCase(key)){
                        logger.info(fName + "has true 3");
                        hasKey=true;
                    }
                }
            }
            return hasKey;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean hasKeyParameterValue(Map<String, List<String>> parameters, String key) {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[hasKeyParameterValue]";
        try {
            return hasKeyParameterValue(parameters,key);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean hasKeyParameterValue(Map<String, List<String>> parameters, String key, int index) {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[hasKeyParameterValue]";
        try {
            logger.info(fName+"key="+key+", index="+index+", parameters="+parameters.toString());
            boolean hasKey=false;String key2="";
            if(parameters.containsKey(key)&&!parameters.get(key).isEmpty()){
                logger.info(fName + "has true");
                key2=key;hasKey=true;
            }
            if(!hasKey&&parameters.containsKey(key.toLowerCase())&&!parameters.get(key.toLowerCase()).isEmpty()){
                logger.info(fName + "has true");
                key2=key.toLowerCase();hasKey=true;
            }
            if(!hasKey){
                Set<String> keys=parameters.keySet();
                Iterator<String>keysIterator=keys.iterator();
                while(!hasKey&&keysIterator.hasNext()){
                    key2=keysIterator.next();
                    if(key2.equalsIgnoreCase(key)&&!parameters.get(key2).isEmpty()){
                        logger.info(fName + "has true");
                        hasKey=true;
                    }
                }
            }
            if(!hasKey){logger.warn(fName + "has no key");return false;}
            List<String>list=parameters.get(key2);
            if(list.isEmpty()){logger.warn(fName + "list is null");return false;}
            if(index>=list.size()){logger.warn(fName + "asking index that does not exists");return false;}
            logger.info(fName + "return value");
            String a=list.get(index);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getKeyParameterValue(Map<String, List<String>> parameters, String key) {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[getKeyParameterValue]";
        try {
            return getKeyParameterValue(parameters,key,0);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getKeyParameterValue(Map<String, List<String>> parameters, String key, int index) {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[getKeyParameterValue]";
        try {
            logger.info(fName+"key="+key+", index="+index+", parameters="+parameters.toString());
            boolean hasKey=false;String key2="";
            if(parameters.containsKey(key)&&!parameters.get(key).isEmpty()){
                logger.info(fName + "has true");
                key2=key;hasKey=true;
            }
            if(!hasKey&&parameters.containsKey(key.toLowerCase())&&!parameters.get(key.toLowerCase()).isEmpty()){
                logger.info(fName + "has true");
                key2=key.toLowerCase();hasKey=true;
            }
            if(!hasKey){
                Set<String> keys=parameters.keySet();
                Iterator<String>keysIterator=keys.iterator();
                while(!hasKey&&keysIterator.hasNext()){
                    key2=keysIterator.next();
                    if(key2.equalsIgnoreCase(key)&&!parameters.get(key2).isEmpty()){
                        logger.info(fName + "has true");
                        hasKey=true;
                    }
                }
            }
            if(!hasKey){logger.warn(fName + "has no key");return null;}
            List<String>list=parameters.get(key2);
            if(list.isEmpty()){logger.warn(fName + "list is null");return null;}
            if(index>=list.size()){logger.warn(fName + "asking index that does not exists");return null;}
            logger.info(fName + "return value");
            return list.get(index);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getAlphaNumericString() {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[getAlphaNumericString]";
        try {
            return getAlphaNumericString(20);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    static String getAlphaNumericString(int n) {
        Logger logger = Logger.getLogger(webHelper1.class);String fName="[getAlphaNumericString]";
        try {
            // chose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int)(AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            return sb.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
}
