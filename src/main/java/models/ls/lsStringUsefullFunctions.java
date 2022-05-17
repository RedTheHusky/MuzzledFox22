package models.ls;

import kong.unirest.json.JSONArray;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.Base64Converter;
import org.apache.logging.log4j.util.Chars;
import unirest.shaded.org.apache.commons.codec.binary.BinaryCodec;
import unirest.shaded.org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static models.llGlobalHelper.llPrefixStr;

public interface lsStringUsefullFunctions {

    static String listList2String(List<String>list){
        return listList2String(list,false);
    }
    static String listList2String(List<String>list,String delimiter){
        return listList2String(list,delimiter,false);
    }
    static String listArray2String(JSONArray array){
        return listArray2String(array,false);
    }
    static String listArray2String(JSONArray array,String delimiter){
        return listArray2String(array,delimiter,false);
    }
    static String listList2String(List<String>list, boolean nonull){
       return listList2String(list,", ",nonull);
    }
    static String listList2String(List<String>list,String delimiter, boolean nonull){
        String fName = "[listList2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"nonull="+nonull);
            logger.info(fName+"delimiter="+delimiter);
            if(list==null){
                logger.info(fName+"list is null");
                if(nonull)return "";
                return null;
            }
            if(list.isEmpty()){
                logger.info(fName+"list is empty");
                return "";
            }
            logger.info(fName+"list.size="+list.size());
            StringBuilder str= new StringBuilder();
            str = new StringBuilder("");
            for(int i=0;i<list.size();i++){
                if(i>0)str.append(delimiter);
                str.append(list.get(i));
            }
            logger.info(fName+"str="+str.toString());
            return str.toString();
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            if(nonull)return "";
            return null;}
    }
    static String listArray2String(JSONArray array, boolean nonull){
        return listArray2String(array,", ",nonull);
    }
    static String listArray2String(JSONArray array,String delimiter, boolean nonull){
        String fName = "[listArray2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"nonull="+nonull);
            logger.info(fName+"delimiter="+delimiter);
            if(array==null){
                logger.info(fName+"array is null");
                if(nonull)return "";
                return null;
            }
            if(array.isEmpty()){
                logger.info(fName+"array is empty");
                return "";
            }
            logger.info(fName+"array.length="+array.length());
            StringBuilder str= new StringBuilder("");
            for(int i=0;i<array.length();i++){
                try{
                    Object object=array.get(i);
                    String value="";
                    if(object instanceof String){
                        value=array.getString(i);
                    }
                    if(object instanceof Integer){
                        value= String.valueOf(array.getInt(i));
                    }
                    if(object instanceof Double){
                        value= String.valueOf(array.getInt(i));
                    }
                    if(object instanceof Long){
                        value= String.valueOf(array.getInt(i));
                    }
                    if(object instanceof Float){
                        value= String.valueOf(array.getFloat(i));
                    }
                    if(object instanceof Boolean){
                        value= String.valueOf(array.getBoolean(i));
                    }
                    if(i>0) str.append(delimiter);
                    str.append(value);
                }catch (Exception ex){ logger.error(fName+"exception="+ex); }
            }
            logger.info(fName+"str="+str.toString());
            return str.toString();
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            if(nonull)return "";
            return null;}
    }

    static String arrayStringJoin2String(String [] array,String delimiter){
        return arrayStringJoin2String(array,delimiter,-1,-1,false);
    }
    static String arrayStringJoin2String(String [] array,String delimiter,int startIndex){
        return arrayStringJoin2String(array,delimiter,startIndex,-1,false);
    }
    static String arrayStringJoin2String(String [] array,String delimiter, boolean nonull){
        return arrayStringJoin2String(array,delimiter,-1,-1,nonull);
    }
    static String arrayStringJoin2String(String [] array,String delimiter,int startIndex, boolean nonull){
        return arrayStringJoin2String(array,delimiter,startIndex,-1,nonull);
    }
    static String arrayStringJoin2String(String [] array,String delimiter,int startIndex,int endIndex){
        return arrayStringJoin2String(array,delimiter,startIndex,endIndex,false);
    }
    static String arrayStringJoin2String(String [] array,String delimiter,int startIndex,int endIndex, boolean nonull){
        String fName = "[arrayStringJoin2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"nonull="+nonull+", startIndex="+startIndex+", endIndex="+endIndex+", delimiter="+delimiter);
            if(array==null){
                logger.info(fName+"array is null");
                if(nonull)return "";
                return null;
            }
            if(array.length==0){
                logger.info(fName+"array is empty");
                return "";
            }
            logger.info(fName+"array.length="+array.length);
            StringBuilder str= new StringBuilder("");
            if(startIndex<0)startIndex=0;
            if(endIndex<0||endIndex>array.length)endIndex=array.length;
            if(delimiter==null)delimiter="";
            for(int i=startIndex;i<endIndex;i++){
                if(i>0) str.append(delimiter);
                str.append(array[i]);
            }
            logger.info(fName+"str="+str.toString());
            return str.toString();
        }
        catch (Exception ex){
            logger.error(fName+"exception="+ex);
            if(nonull)return "";
            return null;}
    }

    static List<String> String2ListStringNoNull(String source, String separator){
        String fName = "[String2ListStringNoNull]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "separator="+separator+ ", source="+source);
            List<String> list = new ArrayList<>(Arrays.asList(source.split(separator)));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return new ArrayList<>();}
    }
    static List<String> String2ListStringNoNull(String source){
        String fName = "[String2ListStringNoNull]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            List<String> list = new ArrayList<>(Arrays.asList(source.split("\\s+")));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return new ArrayList<>();}
    }
    static JSONArray String2JSONArrayNoNull(String source, String separator){
        String fName = "[String2ListStringNoNull]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            logger.info(fName + "separator="+separator);
            JSONArray list = new JSONArray(Arrays.asList(source.split(separator)));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return new JSONArray();}
    }
    static JSONArray String2JSONArrayNoNull(String source){
        String fName = "[String2ListStringNoNull]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            JSONArray list = new JSONArray(Arrays.asList(source.split("\\s+")));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return new JSONArray();}
    }
    static List<String> Json2String(JSONArray jsonArray){
        String fName = "[Json2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "jsonArray="+jsonArray.toString());
            List<String> list = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
               list.add(jsonArray.optString(i));
            }
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return new ArrayList<>();}
    }

    static List<String> String2ListString(String source, String separator){
        String fName = "[String2ListString]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            logger.info(fName + "separator="+separator);
            List<String> list = new ArrayList<>(Arrays.asList(source.split(separator)));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }
    static List<String> String2ListString(String source){
        String fName = "[String2ListString]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            List<String> list = new ArrayList<>(Arrays.asList(source.split("\\s+")));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }
    static JSONArray String2JSONArray(String source, String separator){
        String fName = "[String2ListString]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            logger.info(fName + "separator="+separator);
            JSONArray list = new JSONArray(Arrays.asList(source.split(separator)));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }
    static JSONArray String2JSONArray(String source){
        String fName = "[String2ListString]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName + "source="+source);
            JSONArray list = new JSONArray(Arrays.asList(source.split("\\s+")));
            return list;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }

    static boolean isCharAtPositionLowerCase(String str, int position){
        String fName = "[isCharAtPositionLowerCase]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            return !Character.isUpperCase(str.charAt(position));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static boolean isCharAtPositionUpperCase(String str, int position){
        String fName = "[isCharAtPositionUpperCase]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            return Character.isUpperCase(str.charAt(position));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static boolean isCharCodePointAtPositionLowerCase(String str, int position){
        String fName = "[isCharCodePointAtPositionLowerCase]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            return !Character.isUpperCase(str.codePointAt(position));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static boolean isCharCodePointAtPositionUpperCase(String str, int position){
        String fName = "[isCharCodePointAtPositionUpperCase]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            return Character.isUpperCase(str.codePointAt(position));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static String changeCharInPosition(String str, int position, char ch){
        String fName = "[changeCharInPosition]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position+", ch="+ch);
            char[] charArray = str.toCharArray();
            charArray[position] = ch;
            return new String(charArray);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String changeChar2LowerCaseAtPosition(String str, int position){
        String fName = "[changeChar2LowerCaseAtPosition]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            return changeCharInPosition(str, position, Character.toLowerCase(str.charAt(position)));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String changeChar2UpperCaseAtPosition(String str, int position){
        String fName = "[changeChar2UpperCaseAtPosition]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            return changeCharInPosition(str, position, Character.toUpperCase(str.charAt(position)));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String switchCharCaseAtPosition(String str, int position){
        String fName = "[switchCharCaseAtPosition]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", position="+position);
            char c=str.charAt(position);
            if(Character.isUpperCase(c)){
                return changeCharInPosition(str, position, Character.toLowerCase(c));
            }else
            if(Character.isLowerCase(c)){
                return changeCharInPosition(str, position, Character.toUpperCase(c));
            }else{
                return str;
            }
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static boolean containsSpecialCharacters(String str){
        String fName = "[containsSpecialCharacters]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            boolean b = m.find();
            return  b;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static boolean containsSpecialCharacters(Character c){
        String fName = "[containsSpecialCharacters]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            return containsSpecialCharacters(c.toString());
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static boolean containsSpecialCharacters(char c){
        String fName = "[containsSpecialCharacters]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
           return containsSpecialCharacters(Character.valueOf(c));
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static int getSpecialCharacterCount(String str){
        String fName = "[getSpecialCharacterCount]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            int theCount = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.substring(i, 1).matches("[^A-Za-z0-9 ]")) {
                    theCount++;
                }
            }
            return theCount;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
    }
    static String removeSpecialCharacters4String(String str){
        String fName = "[removeSpecialCharacters4String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            str=str.replaceAll("[^a-zA-Z0-9]","");
            logger.info(fName+"result="+str);
            return  str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String replaceSpecialCharacters4String(String str,String with){
        String fName = "[replaceSpecialCharacters4String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", with="+with);
            str=str.replaceAll("[^a-zA-Z0-9]",with);
            logger.info(fName+"result="+str);
            return  str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String removeSpecialCharacters4String(String str,String def){
        String fName = "[removeSpecialCharacters4String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", def="+def);
            str=str.replaceAll("["+def+"]*","");
            logger.info(fName+"result="+str);
            return  str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String replaceSpecialCharacters4String(String str,String with,String def){
        String fName = "[replaceSpecialCharacters4String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", with="+with+", def="+def);
            str=str.replaceAll("["+def+"]*",with);
            logger.info(fName+"result="+str);
            return  str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String remove4SpecialCharacter(String str){
        String fName = "[removee4SpecialCharacters]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            for(int i=0;i<str.length();i++){
                char c=str.charAt(i);
                if(containsSpecialCharacters(c)){
                    logger.info(fName+"found special character>remove from here");
                    str=str.substring(0,i);
                    break;
                }
            }
            logger.info(fName+"new str="+str);
            return  str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static String replace4SpecialCharacter(String str,String with){
        String fName = "[replace4SpecialCharacters]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", with="+with);
            for(int i=0;i<str.length();i++){
                char c=str.charAt(i);
                if(containsSpecialCharacters(c)){
                    logger.info(fName+"found special character>remove from here");
                    str=str.substring(0,i-1);
                    str+=with;
                    break;
                }
            }
            logger.info(fName+"new str="+str);
            return  str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    static int String2Int(String str){
        String fName = "[String2Int]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            int number=Integer.parseInt(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
    }
    static long String2Long(String str){
        String fName = "[String2Long]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            long number=Long.parseLong(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
    }
    static float String2Float(String str){
        String fName = "[String2Float]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            float number=Float.parseFloat(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
    }
    static double String2Double(String str){
        String fName = "[String2Float]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            double number=Double.parseDouble(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return 0;}
    }
    static BigInteger String2BigInteger(String str){
        String fName = "[String2BigInteger]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            BigInteger number=new BigInteger(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return new BigInteger("0");}
    }
    static boolean String2Boolean(String str){
        String fName = "[String2Boolean]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            boolean number;
            switch (str.toLowerCase()){
                case "1":
                case "true":
                    number=true;break;
                case "0":
                case "false":
                    number=false;break;
                default:
                    number=Boolean.parseBoolean(str);
            }
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }

    static int String2Int(String str,int def){
        String fName = "[String2Int]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            int number=Integer.parseInt(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return def;}
    }
    static long String2Long(String str, long def){
        String fName = "[String2Long]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            long number=Long.parseLong(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return def;}
    }
    static float String2Float(String str, float def){
        String fName = "[String2Float]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            float number=Float.parseFloat(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return def;}
    }
    static double String2Double(String str, double def){
        String fName = "[String2Float]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            double number=Double.parseDouble(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return def;}
    }
    static BigInteger String2BigInteger(String str, BigInteger def){
        String fName = "[String2BigInteger]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            BigInteger number=new BigInteger(str);
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);
            if(def!=null)return def;
            else return new BigInteger("0");
        }
    }
    static boolean String2Boolean(String str, boolean def){
        String fName = "[String2Boolean]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            boolean number;
            switch (str.toLowerCase()){
                case "1":
                case "true":
                    number=true;break;
                case "0":
                case "false":
                    number=false;break;
                default:
                    number=Boolean.parseBoolean(str);
            }
            logger.info(fName+"number="+number);
            return number;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return def;}
    }

    static String text2Fraktur(String str){
        String fName = "[text2Fraktur]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            StringBuilder newtext= new StringBuilder();
            logger.info(fName+"source="+str);
            String originalChars="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
            //String convertedChars="\uD835\uDD7C\uD835\uDD82\uD835\uDD70\uD835\uDD7D\uD835\uDD7F\uD835\uDD84\uD835\uDD80\uD835\uDD74\uD835\uDD7A\uD835\uDD7B\uD835\uDD6C\uD835\uDD7E\uD835\uDD6F\uD835\uDD71\uD835\uDD72\uD835\uDD73\uD835\uDD75\uD835\uDD76\uD835\uDD77\uD835\uDD85\uD835\uDD83\uD835\uDD6E\uD835\uDD81\uD835\uDD6D\uD835\uDD79\uD835\uDD78\uD835\uDD96\uD835\uDD9C\uD835\uDD8A\uD835\uDD97\uD835\uDD99\uD835\uDD9E\uD835\uDD9A\uD835\uDD8E\uD835\uDD94\uD835\uDD95\uD835\uDD86\uD835\uDD98\uD835\uDD89\uD835\uDD8B\uD835\uDD8C\uD835\uDD8D\uD835\uDD8F\uD835\uDD90\uD835\uDD91\uD835\uDD9F\uD835\uDD9D\uD835\uDD88\uD835\uDD9B\uD835\uDD87\uD835\uDD93\uD835\uDD92";
            ArrayList<String>converted=new ArrayList<>();
            converted.add("\uD835\uDD7C"); converted.add("\uD835\uDD82"); converted.add("\uD835\uDD70"); converted.add("\uD835\uDD7D"); converted.add("\uD835\uDD7F"); converted.add("\uD835\uDD84"); converted.add("\uD835\uDD80"); converted.add("\uD835\uDD74"); converted.add("\uD835\uDD7A"); converted.add("\uD835\uDD7B"); converted.add("\uD835\uDD6C"); converted.add("\uD835\uDD7E"); converted.add("\uD835\uDD6F"); converted.add("\uD835\uDD71"); converted.add("\uD835\uDD72"); converted.add("\uD835\uDD73"); converted.add("\uD835\uDD75"); converted.add("\uD835\uDD76"); converted.add("\uD835\uDD77"); converted.add("\uD835\uDD85"); converted.add("\uD835\uDD83"); converted.add("\uD835\uDD6E"); converted.add("\uD835\uDD81"); converted.add("\uD835\uDD6D"); converted.add("\uD835\uDD79"); converted.add("\uD835\uDD78"); converted.add("\uD835\uDD96"); converted.add("\uD835\uDD9C"); converted.add("\uD835\uDD8A"); converted.add("\uD835\uDD97"); converted.add("\uD835\uDD99"); converted.add("\uD835\uDD9E"); converted.add("\uD835\uDD9A"); converted.add("\uD835\uDD8E"); converted.add("\uD835\uDD94"); converted.add("\uD835\uDD95"); converted.add("\uD835\uDD86"); converted.add("\uD835\uDD98"); converted.add("\uD835\uDD89"); converted.add("\uD835\uDD8B"); converted.add("\uD835\uDD8C"); converted.add("\uD835\uDD8D"); converted.add("\uD835\uDD8F"); converted.add("\uD835\uDD90"); converted.add("\uD835\uDD91"); converted.add("\uD835\uDD9F"); converted.add("\uD835\uDD9D"); converted.add("\uD835\uDD88"); converted.add("\uD835\uDD9B"); converted.add("\uD835\uDD87"); converted.add("\uD835\uDD93"); converted.add("\uD835\uDD92");
            logger.info(fName+"converted="+converted.toString());
            for(int i=0;i<str.length();i++){
                char ch=str.charAt(i);
                logger.info(fName+"ch["+i+"]="+ch);
                if(originalChars.contains(String.valueOf(ch))){
                    logger.info(fName+"contain such character");
                    int j=originalChars.indexOf(String.valueOf(ch));
                    logger.info(fName+"j="+ j);
                    if(j<converted.size()){
                        logger.info(fName+"place new char="+converted.get(j));
                        newtext.append(converted.get(j));
                    }else{
                        logger.info(fName+"place old char="+str.charAt(i));
                        newtext.append(str.charAt(i));
                    }
                }else{
                    newtext.append(str.charAt(i));
                }
            }
            logger.info(fName+"newtext="+newtext.toString());
            return newtext.toString();
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
        return null;}
    }
    static String text2DoubleStruck(String str){
        String fName = "[text2DoubleStruck]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            StringBuilder newtext= new StringBuilder();
            logger.info(fName+"source="+str);
            String originalChars="QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm1234567890";
            String convertedChars="ℚ𝕎𝔼ℝ𝕋𝕐𝕌𝕀𝕆ℙ𝔸𝕊𝔻𝔽𝔾ℍ𝕁𝕂𝕃ℤ𝕏ℂ𝕍𝔹ℕ𝕄𝕢𝕨𝕖𝕣𝕥𝕪𝕦𝕚𝕠𝕡𝕒𝕤𝕕𝕗𝕘𝕙𝕛𝕜𝕝𝕫𝕩𝕔𝕧𝕓𝕟𝕞𝟙𝟚𝟛𝟜𝟝𝟞𝟟𝟠𝟡𝟘";
                    ArrayList<String>converted=new ArrayList<>();
            converted.add("\uD835\uDD7C"); converted.add("\uD835\uDD82"); converted.add("\uD835\uDD70"); converted.add("\uD835\uDD7D"); converted.add("\uD835\uDD7F"); converted.add("\uD835\uDD84"); converted.add("\uD835\uDD80"); converted.add("\uD835\uDD74"); converted.add("\uD835\uDD7A"); converted.add("\uD835\uDD7B"); converted.add("\uD835\uDD6C"); converted.add("\uD835\uDD7E"); converted.add("\uD835\uDD6F"); converted.add("\uD835\uDD71"); converted.add("\uD835\uDD72"); converted.add("\uD835\uDD73"); converted.add("\uD835\uDD75"); converted.add("\uD835\uDD76"); converted.add("\uD835\uDD77"); converted.add("\uD835\uDD85"); converted.add("\uD835\uDD83"); converted.add("\uD835\uDD6E"); converted.add("\uD835\uDD81"); converted.add("\uD835\uDD6D"); converted.add("\uD835\uDD79"); converted.add("\uD835\uDD78"); converted.add("\uD835\uDD96"); converted.add("\uD835\uDD9C"); converted.add("\uD835\uDD8A"); converted.add("\uD835\uDD97"); converted.add("\uD835\uDD99"); converted.add("\uD835\uDD9E"); converted.add("\uD835\uDD9A"); converted.add("\uD835\uDD8E"); converted.add("\uD835\uDD94"); converted.add("\uD835\uDD95"); converted.add("\uD835\uDD86"); converted.add("\uD835\uDD98"); converted.add("\uD835\uDD89"); converted.add("\uD835\uDD8B"); converted.add("\uD835\uDD8C"); converted.add("\uD835\uDD8D"); converted.add("\uD835\uDD8F"); converted.add("\uD835\uDD90"); converted.add("\uD835\uDD91"); converted.add("\uD835\uDD9F"); converted.add("\uD835\uDD9D"); converted.add("\uD835\uDD88"); converted.add("\uD835\uDD9B"); converted.add("\uD835\uDD87"); converted.add("\uD835\uDD93"); converted.add("\uD835\uDD92");
            logger.info(fName+"converted="+converted.toString());
            for(int i=0;i<str.length();i++){
                char ch=str.charAt(i);
                logger.info(fName+"ch["+i+"]="+ch);
                if(originalChars.contains(String.valueOf(ch))){
                    logger.info(fName+"contain such character");
                    int j=originalChars.indexOf(String.valueOf(ch));
                    logger.info(fName+"j="+ j);
                    if(j<converted.size()){
                        logger.info(fName+"place new char="+convertedChars.charAt(j));
                        newtext.append(convertedChars.charAt(j));
                    }else{
                        logger.info(fName+"place old char="+str.charAt(i));
                        newtext.append(str.charAt(i));
                    }
                }else{
                    newtext.append(str.charAt(i));
                }
            }
            logger.info(fName+"newtext="+newtext.toString());
            return newtext.toString();
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return null;}
    }
    static String StringAt(String str, int index){
        String fName = "[StringAt]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str+", index="+index);
            char c=str.charAt(index);
            str=String.valueOf(c);
            logger.info(fName+"char="+str);
            return str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "";}
    }
    Pattern pattern4Numeric= Pattern.compile("-?\\d+(\\.\\d+)?");
    static boolean isNumeric(String str){
        String fName = "[isNumeric]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"source="+str);
            if(str==null)return false;
            boolean result=pattern4Numeric.matcher(str).matches();
            logger.info(fName+"result="+result);
            return result;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
     final char[] zalgo_up =
            { '\u030d', /*     Ì?     */'\u030e', /*     ÌŽ     */'\u0304', /*     Ì„     */'\u0305', /*     Ì…     */
                    '\u033f', /*     Ì¿     */'\u0311', /*     Ì‘     */'\u0306', /*     Ì†     */'\u0310', /*     Ì?     */
                    '\u0352', /*     Í’     */'\u0357', /*     Í—     */'\u0351', /*     Í‘     */'\u0307', /*     Ì‡     */
                    '\u0308', /*     Ìˆ     */'\u030a', /*     ÌŠ     */'\u0342', /*     Í‚     */'\u0343', /*     Ì“     */
                    '\u0344', /*     ÌˆÌ?     */'\u034a', /*     ÍŠ     */'\u034b', /*     Í‹     */'\u034c', /*     ÍŒ     */
                    '\u0303', /*     Ìƒ     */'\u0302', /*     Ì‚     */'\u030c', /*     ÌŒ     */'\u0350', /*     Í?     */
                    '\u0300', /*     Ì€     */'\u0301', /*     Ì?     */'\u030b', /*     Ì‹     */'\u030f', /*     Ì?     */
                    '\u0312', /*     Ì’     */'\u0313', /*     Ì“     */'\u0314', /*     Ì”     */'\u033d', /*     Ì½     */
                    '\u0309', /*     Ì‰     */'\u0363', /*     Í£     */'\u0364', /*     Í¤     */'\u0365', /*     Í¥     */
                    '\u0366', /*     Í¦     */'\u0367', /*     Í§     */'\u0368', /*     Í¨     */'\u0369', /*     Í©     */
                    '\u036a', /*     Íª     */'\u036b', /*     Í«     */'\u036c', /*     Í¬     */'\u036d', /*     Í­     */
                    '\u036e', /*     Í®     */'\u036f', /*     Í¯     */'\u033e', /*     Ì¾     */'\u035b', /*     Í›     */
                    '\u0346', /*     Í†     */'\u031a' /*     Ìš     */
            } ;

     final char[] zalgo_down =
            { '\u0316', /*     Ì–     */'\u0317', /*     Ì—     */'\u0318', /*     Ì˜     */'\u0319', /*     Ì™     */
                    '\u031c', /*     Ìœ     */'\u031d', /*     Ì?     */'\u031e', /*     Ìž     */'\u031f', /*     ÌŸ     */
                    '\u0320', /*     Ì      */'\u0324', /*     Ì¤     */'\u0325', /*     Ì¥     */'\u0326', /*     Ì¦     */
                    '\u0329', /*     Ì©     */'\u032a', /*     Ìª     */'\u032b', /*     Ì«     */'\u032c', /*     Ì¬     */
                    '\u032d', /*     Ì­     */'\u032e', /*     Ì®     */'\u032f', /*     Ì¯     */'\u0330', /*     Ì°     */
                    '\u0331', /*     Ì±     */'\u0332', /*     Ì²     */'\u0333', /*     Ì³     */'\u0339', /*     Ì¹     */
                    '\u033a', /*     Ìº     */'\u033b', /*     Ì»     */'\u033c', /*     Ì¼     */'\u0345', /*     Í…     */
                    '\u0347', /*     Í‡     */'\u0348', /*     Íˆ     */'\u0349', /*     Í‰     */'\u034d', /*     Í?     */
                    '\u034e', /*     ÍŽ     */'\u0353', /*     Í“     */'\u0354', /*     Í”     */'\u0355', /*     Í•     */
                    '\u0356', /*     Í–     */'\u0359', /*     Í™     */'\u035a', /*     Íš     */'\u0323' /*     Ì£     */
            } ;

    //those always stay in the middle
     final char[] zalgo_mid =
            { '\u0315', /*     Ì•     */'\u031b', /*     Ì›     */'\u0340', /*     Ì€     */'\u0341', /*     Ì?     */
                    '\u0358', /*     Í˜     */'\u0321', /*     Ì¡     */'\u0322', /*     Ì¢     */'\u0327', /*     Ì§     */
                    '\u0328', /*     Ì¨     */'\u0334', /*     Ì´     */'\u0335', /*     Ìµ     */'\u0336', /*     Ì¶     */
                    '\u034f', /*     Í?     */'\u035c', /*     Íœ     */'\u035d', /*     Í?     */'\u035e', /*     Íž     */
                    '\u035f', /*     ÍŸ     */'\u0360', /*     Í      */'\u0362', /*     Í¢     */'\u0338', /*     Ì¸     */
                    '\u0337', /*     Ì·     */'\u0361', /*     Í¡     */'\u0489' /*     Ò‰_     */
            } ;
    static boolean is_zalgo_char(char c) {
        String fName = "[is_zalgo_char]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"c="+c);
            for (int i = 0; i < zalgo_up.length; i++)
                if (c == zalgo_up[i])
                    return true;
            for (int i = 0; i < zalgo_down.length; i++)
                if (c == zalgo_down[i])
                    return true;
            for (int i = 0; i < zalgo_mid.length; i++)
                if (c == zalgo_mid[i])
                    return true;
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static char rand_zalgo(char[] array) {
        String fName = "[rand_zalgo]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"array="+array.toString());
            int ind = (int)Math.floor(Math.random() * array.length);
            return array[ind];
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return ' ';
        }

    }
    static String string2Zalgo(String source) {
        //https://stackoverflow.com/questions/26927419/zalgo-text-in-java
        String fName = "[string2Zalgo]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        logger.info(fName);
        try {
            logger.info(fName+"source="+source);
            String zalgoTxt = "";
            boolean up=true, mid=true,down=true;
            if(source.contains("!up")){
                up=false; source=source.replaceAll("!up","");
            }
            if(source.contains("!down")){
                up=false; source=source.replaceAll("!down","");
            }
            if(source.contains("!mid")){
                up=false; source=source.replaceAll("!mid","");
            }
            boolean zalgo_opt_mini2=false,zalgo_opt_mini=true,zalgo_opt_normal=false,zalgo_opt_corrupted=false;
            if(source.contains("!mini2")){
                zalgo_opt_mini2=true;
                source=source.replaceAll("!mini2","");
            }
            if(source.contains("!mini")){
                zalgo_opt_mini=true;zalgo_opt_normal=false;
                source=source.replaceAll("!mini","");
            }
            if(source.contains("!normal")){
                zalgo_opt_mini=false;zalgo_opt_normal=true;
                source=source.replaceAll("!normal","");
            }
            if(source.contains("!maxi")){
                zalgo_opt_mini=false;zalgo_opt_corrupted=false;
                source=source.replaceAll("!maxi","");
            }
            if(source.contains("!corrupted")){
                zalgo_opt_mini=false;zalgo_opt_corrupted=true;
                source=source.replaceAll("!corrupted","");
            }
            if(source.contains("!fraktur")){
                source= lsStringUsefullFunctions.text2Fraktur(source.replaceAll("!fraktur",""));
                logger.info(fName+"new source="+source);
            }
            if(source.contains("!double")){
                source= lsStringUsefullFunctions.text2DoubleStruck(source.replaceAll("!double",""));
                logger.info(fName+"new source="+source);
            }
            for (int i = 0; i <source.length(); i++) {
                if (is_zalgo_char(source.charAt(i)))
                    continue;

                int num_up;
                int num_mid;
                int num_down;

                //add the normal character
                zalgoTxt += source.charAt(i);

                //options
                if (zalgo_opt_mini2) {
                    num_up = lsUsefullFunctions.getRandom(4);
                    num_mid = lsUsefullFunctions.getRandom(2);
                    num_down = lsUsefullFunctions.getRandom(4);
                }
                else if (zalgo_opt_mini) {
                    num_up = lsUsefullFunctions.getRandom(8);
                    num_mid = lsUsefullFunctions.getRandom(2);
                    num_down = lsUsefullFunctions.getRandom(8);
                } else if (zalgo_opt_normal) {
                    num_up = lsUsefullFunctions.getRandom(16) / 2 + 1;
                    num_mid = lsUsefullFunctions.getRandom(6) / 2;
                    num_down = lsUsefullFunctions.getRandom(16) / 2 + 1;
                } else if (zalgo_opt_corrupted) {
                    num_up = lsUsefullFunctions.getRandom(16) / 2 + 1;
                    num_mid = lsUsefullFunctions.getRandom(16,32) / 2+1 ;
                    num_down = lsUsefullFunctions.getRandom(16) / 2 + 1;
                }
                else //maxi
                {
                    num_up = lsUsefullFunctions.getRandom(64) / 4 + 3;
                    num_mid =lsUsefullFunctions.getRandom(16) / 4 + 1;
                    num_down = lsUsefullFunctions.getRandom(64) / 4 + 3;
                }
                logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                logger.info(fName+"num_up ="+num_up +", num_mid="+num_mid+", num_down="+num_down);
                    if (up)
                        for (int j = 0; j < num_up; j++){
                            zalgoTxt += rand_zalgo(zalgo_up);
                            logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                        }

                    if (mid)
                        for (int j = 0; j < num_mid; j++){
                            zalgoTxt += rand_zalgo(zalgo_mid);
                            logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                        }
                    if (down)
                        for (int j = 0; j < num_down; j++){
                            zalgoTxt += rand_zalgo(zalgo_down);
                            logger.info(fName+"zalgoTxt.lengthe="+zalgoTxt.length()+", zalgoTxt="+zalgoTxt);
                        }


            }



            return zalgoTxt;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    static String llRemoveSubString4LastIndexOf(String source,String find) {
        //https://stackoverflow.com/questions/5408796/remove-the-last-part-of-a-string-in-java
        String fName = "[llRemoveSubString4LastIndexOf]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        logger.info(fName);
        try {
            int i=source.lastIndexOf(find);
            logger.info(fName+"source="+source+", find="+find+", index="+i);
            source=source.substring(0,i);
            logger.info(fName+"result="+source);
            return source;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    //http://string-functions.com
    static String toHex(String arg) {
        String fName = "[llRemoveSubString4LastIndexOf]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        logger.info(fName);
        try {
            return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    static String string2Hex(String str) {
        String fName = "[String2Hex]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            // display in uppercase
            //char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8), false);

            // display in lowercase, default
            logger.info(fName+"input="+str);
            String result= Hex.encodeHexString(str.getBytes(StandardCharsets.UTF_8));
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String hex2String(String hex) {
        String fName = "[Hex2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            // display in uppercase
            //char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8), false);

            // display in lowercase, default
            logger.info(fName+"input="+hex);
            byte[] bytes = Hex.decodeHex(hex);
            String result = new String(bytes, StandardCharsets.UTF_8);
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String string2Binary(String str) {
        String fName = "[String2Binary]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"input="+str);
            StringBuilder result = new StringBuilder();
            char[] chars = str.toCharArray();
            for (char aChar : chars) {
                result.append(
                        String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                                .replaceAll(" ", "0")                         // zero pads
                );
            }
            logger.info(fName+"result="+result.toString());
            return result.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String string2BinaryBlocksAsString(String str, int blockSize, String separator) {
        String fName = "[String2Binary]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"input="+str);
            String binary=string2Binary(str);
            logger.info(fName+"binary="+binary);
            List<String> array = new ArrayList<>();
            int index = 0;
            while (index < binary.length()) {
                array.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
                index += blockSize;
            }
            logger.info(fName+"array="+array.toString());
            String result=array.stream().collect(Collectors.joining(separator));
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static List<String> string2BinaryBlocks(String str, int blockSize){
        String fName = "[String2BinaryBlocks]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info(fName+"input="+str);
            String binary=string2Binary(str);
            logger.info(fName+"binary="+binary);
            List<String> result = new ArrayList<>();
            int index = 0;
            while (index < binary.length()) {
                result.add(binary.substring(index, Math.min(index + blockSize, binary.length())));
                index += blockSize;
            }
            logger.info(fName+"result="+result.toString());
            return result;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return  null;}
    }
    static String binary2String(String binary) {
        String fName = "[Binary2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"binary="+binary);
            StringBuilder result= new StringBuilder();
            for(int index = 0; index < binary.length(); index+=9) {
                String temp = binary.substring(index, index+8);
                int num = Integer.parseInt(temp,2);
                char letter = (char) num;
                result.append(letter);
            }
            logger.info(fName+"result="+result.toString());
            return result.toString();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String string2Base64(String str) {
        String fName = "[string2Base64]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"input="+str);
            byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
            String result=  new String(bytesEncoded);
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String base64ToString(String str) {
        String fName = "[base64ToString]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName+"input="+str);
            byte[] bytesDecode = Base64.decodeBase64(str.getBytes());
            String result=  new String(bytesDecode);
            logger.info(fName+"result="+result);
            return result;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String BufferedReader2String(BufferedReader bufferedReader) {
        String fName = "[BufferedReader2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName);
            if(bufferedReader==null){
                logger.warn(fName+".is null");return  "";
            }
            String str;
            StringBuilder buff= new StringBuilder();
            int i=0;
            while ((str = bufferedReader.readLine()) != null) {
                logger.info(fName+".line="+i);i++;
                if(2147483600<=i){i=0;}
                buff.append(str);
            }
            str=buff.toString();
            logger.info(fName+".string="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String InputStreamReader2String(InputStreamReader inputStreamReader) {
        String fName = "[InputStreamReader2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName);
            if(inputStreamReader==null){
                logger.warn(fName+".is null");return  "";
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            return BufferedReader2String(reader);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String InputStream2String(InputStream inputStream) {
        String fName = "[InputStream2String]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try {
            logger.info(fName);
            if(inputStream==null){
                logger.warn(fName+".is null");return  "";
            }
            InputStreamReader reader=new InputStreamReader(inputStream);
            return InputStreamReader2String(reader);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }


    static String truncateStringIfOver(String source,int size){
        return truncateStringIfOver(source,size,"");
    }
    static String truncateStringIfOver(String source,int size,String append){
        String fName = "[truncateStringIfOver]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info("source="+source+", size="+size+", append="+append);
            if(source.length()<=size){
                return  source;
            }
            source=source.substring(0,size);
            if(append!=null&&!append.isEmpty()&&append.length()<=size) {
                source=source.substring(0,source.length()-append.length()+1);
                source+=append;
            }
            return source;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return null;}
    }
    static Character getCharAt(String source,int index){
        String fName = "[getCharAt]";Logger logger = Logger.getLogger(lsStringUsefullFunctions.class);
        try{
            logger.info("source="+source+",index="+index);
            Character character=source.charAt(index);
            logger.info("character="+character);
            return character;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex);
            logger.error(fName + ".exception:" + Arrays.toString(ex.getStackTrace()));
            return null;}
    }

}
