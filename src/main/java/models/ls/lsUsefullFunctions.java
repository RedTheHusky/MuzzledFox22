package models.ls;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

import static models.llGlobalHelper.llPrefixStr;

public interface lsUsefullFunctions {
    static int getRandom(int max){
        String fName="[getRandom]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            if(max<=0){return 0;}
            Random rand = new Random();
            return  rand.nextInt(max);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }

    }
    static int getRandomToMax(int max){
        String fName="[getRandomToMax]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            if(max<=0){return 0;}
            Random rand = new Random();
            return  rand.nextInt(max+1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    static int getRandom(int min,int max){
        String fName="[getRandom]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            if(max<=0){return 0;}
            if(min<=0){return 0;}
            if (min >= max) {
                return 0;
            }
            Random rand = new Random();
            return  rand.nextInt((max - min) + 1) + min;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    static String randomCode(int size){
        String fName="[randomCode]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            // length is bounded by 256 Character
            int n=size;
            byte[] array = new byte[256];
            new Random().nextBytes(array);

            String randomString
                    = new String(array, StandardCharsets.UTF_8);

            // Create a StringBuffer to store the result
            StringBuffer r = new StringBuffer();

            // remove all spacial char
            String  AlphaNumericString
                    = randomString
                    .replaceAll("[^A-Za-z0-9]", "");

            // Append first 20 alphanumeric characters
            // from the generated random String into the result
            for (int k = 0; k < AlphaNumericString.length(); k++) {

                if (Character.isLetter(AlphaNumericString.charAt(k))
                        && (n > 0)
                        || Character.isDigit(AlphaNumericString.charAt(k))
                        && (n > 0)) {

                    r.append(AlphaNumericString.charAt(k));
                    n--;
                }
            }

            // return the resultant string
            return r.toString();
        }catch (Exception e){
            logger.error(".exception=" + e);
            logger.error(".exception:" + Arrays.toString(e.getStackTrace()));
            return "null";
        }
    }
    static boolean isFirstCharInStringUpper(String text){
        return Character.isUpperCase(text.charAt(0));
    }
    static boolean isStringUpper(String text){
        char[] charArray =text.toCharArray();
        for(int i=0; i < charArray.length; i++){
            if( !Character.isUpperCase( charArray[i] ))
                return false;
        }
        return true;
    }
    static String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }
    static String insertStr(String str, String insert, int index) {
       String first="";
       String last="";
        if (index != 0) {
            first=str.substring(0,index-1);
        }
        if (index < str.length()-1) {
            last=str.substring(index+1,-1);
        }
        return first+"zahh"+last;
    }
    static String link4Embed(String name, String url){
        return "["+name+"]("+url+")";
    }
    static String convertOffsetDateTime2HumanReadable(OffsetDateTime time) {
        String fName="convertOffsetDateTime2HumanReadable.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String year= String.valueOf(time.getYear());
            String month=String.valueOf(time.getMonthValue()+1);
            String day=String.valueOf(time.getDayOfMonth());
            String hour=String.valueOf(time.getHour());
            String minute=String.valueOf(time.getMinute());
            if(month.length()<2){
                month="0"+month;
            }
            if(day.length()<2){
                day="0"+day;
            }
            if(hour.length()<2){
                hour="0"+hour;
            }
            if(minute.length()<2){
                minute="0"+minute;
            }
            return year+"."+month+"."+day+" "+hour+":"+minute;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String convertOffsetDateTime2HumanReadable(Long l) {
        String fName="convertOffsetDateTime2HumanReadable.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            OffsetDateTime time=OffsetDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault());
            String year= String.valueOf(time.getYear());
            String month=String.valueOf(time.getMonthValue());
            String day=String.valueOf(time.getDayOfMonth());
            String hour=String.valueOf(time.getHour());
            String minute=String.valueOf(time.getMinute());
            if(month.length()<2){
                month="0"+month;
            }
            if(day.length()<2){
                day="0"+day;
            }
            if(hour.length()<2){
                hour="0"+hour;
            }
            if(minute.length()<2){
                minute="0"+minute;
            }
            return year+"."+month+"."+day+" "+hour+":"+minute;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String convertOffsetDateTime2HumanReadableOnlyCalendar(OffsetDateTime time) {
        String fName="convertOffsetDateTime2HumanReadableOnlyCalendar.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String year= String.valueOf(time.getYear());
            String month=String.valueOf(time.getMonthValue()+1);
            String day=String.valueOf(time.getDayOfMonth());

            if(month.length()<2){
                month="0"+month;
            }
            if(day.length()<2){
                day="0"+day;
            }
            return year+"."+month+"."+day;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String convertOffsetDateTime2HumanReadableOnlyTime(OffsetDateTime time) {
        String fName="convertOffsetDateTime2HumanReadableOnlyTime.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String hour=String.valueOf(time.getHour());
            String minute=String.valueOf(time.getMinute());
            if(hour.length()<2){
                hour="0"+hour;
            }
            if(minute.length()<2){
                minute="0"+minute;
            }
            return hour+":"+minute;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static boolean isStringJustLong(String str) {
        String fName="isStringJustLong.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if(Pattern.matches("[a-zA-Z]+", str)){
                return false;
            }
            long l=Long.parseLong(str);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isStringJustDouble(String str) {
        String fName="isStringJustDouble.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if(Pattern.matches("[a-zA-Z]+", str)){
                return false;
            }
            double d=Double.parseDouble(str);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isStringJustInteger(String str) {
        String fName="isStringJustInteger.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if(Pattern.matches("[a-zA-Z]+", str)){
                return false;
            }
            int i=Integer.parseInt(str);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean isStringJustBoolean(String str) {
        String fName="isStringJustBoolean.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            boolean b=Boolean.getBoolean(str);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static long getStringJustLong(String str) {
        String fName="isStringJustLong.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if(Pattern.matches("[a-zA-Z]+", str)){
                return 0;
            }
            return Long.parseLong(str);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    static double getStringJustDouble(String str) {
        String fName="isStringJustDouble.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if(Pattern.matches("[a-zA-Z]+", str)){
                return 0;
            }
            return Double.parseDouble(str);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    static int getStringJustInteger(String str) {
        String fName="isStringJustInteger.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if(Pattern.matches("[a-zA-Z]+", str)){
                return 0;
            }
            return Integer.parseInt(str);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    static boolean getStringJustBoolean(String str) {
        String fName="isStringJustBoolean.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            return Boolean.getBoolean(str);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String displayDuration(Long time){
        String fName = "[displayDuration]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);


        try{
            if(time==null){
                logger.info(fName+"time is null");
                return "null";
            }
            logger.info(fName+"time="+time);
            long week = time / lsNumbersUsefullFunctions.milliseconds_week;
            long diffWeek = time % lsNumbersUsefullFunctions.milliseconds_week;
            long day = diffWeek / lsNumbersUsefullFunctions.milliseconds_day;
            long diffDay = diffWeek % lsNumbersUsefullFunctions.milliseconds_day;
            long hour = diffDay / lsNumbersUsefullFunctions.milliseconds_hour;
            long diffHour = diffDay % lsNumbersUsefullFunctions.milliseconds_hour;
            long minutes = diffHour / lsNumbersUsefullFunctions.milliseconds_minute;
            logger.info(fName+"week="+week);
            logger.info(fName+"diffWeek="+diffWeek);
            logger.info(fName+"day="+day);
            logger.info(fName+"time="+diffDay);
            logger.info(fName+"hour="+hour);
            logger.info(fName+"minutes="+minutes);
            String str="";
            if(week>1){
                str+=week+" weeks ";
            }else
            if(week==1){
                str+=week+" week ";
            }
            if(day>1){
                str+=day+" days ";
            }else
            if(day==1){
                str+=day+" day ";
            }
            if(hour>1){
                str+=hour+" hours ";
            }else
            if(hour==1){
                str+=hour+" hour ";
            }
            if(minutes>1){
                str+=minutes+" minutes ";
            }else
            if(minutes==1){
                str+=minutes+" minute ";
            }
            logger.info(fName+"str="+str);
            return str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return "null";}
    }
    static String convertCalendar2HumanReadable(Calendar calendar) {
        String fName="convertCalendar2HumanReadable.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String year= String.valueOf(calendar.get(calendar.YEAR));
            String month= String.valueOf(calendar.get(calendar.MONTH)+1);
            String day=String.valueOf(calendar.get(calendar.DAY_OF_MONTH));
            String hours=String.valueOf(calendar.get(calendar.HOUR_OF_DAY));
            String minutes=String.valueOf(calendar.get(calendar.MINUTE));
            if(year.length()>0){
                year=year.substring(2,4);
            }
            if(month.length()==1){
                month="0"+month;
            }
            if(day.length()==1){
                day="0"+day;
            }
            if(hours.length()==1){
                hours="0"+hours;
            }
            if(minutes.length()==1){
                minutes="0"+minutes;
            }
            String str=year+"."+month+"."+day+" "+hours+":"+minutes;
            logger.info(fName+".output="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String convertCalendar2HumanReadableOnlyDate(Calendar calendar) {
        String fName="convertCalendar2HumanReadableOnlyDate.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String year= String.valueOf(calendar.get(calendar.YEAR));
            String month= String.valueOf(calendar.get(calendar.MONTH)+1);
            String day=String.valueOf(calendar.get(calendar.DAY_OF_MONTH));
            if(year.length()>0){
                year=year.substring(2,4);
            }
            if(month.length()==1){
                month="0"+month;
            }
            if(day.length()==1){
                day="0"+day;
            }
            String str=year+"."+month+"."+day;
            logger.info(fName+".output="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String convertCalendar2HumanReadableOnlyTime(Calendar calendar) {
        String fName="convertCalendar2HumanReadableOnlyTime.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String hours=String.valueOf(calendar.get(calendar.HOUR_OF_DAY));
            String minutes=String.valueOf(calendar.get(calendar.MINUTE));
            if(hours.length()==1){
                hours="0"+hours;
            }
            if(minutes.length()==1){
                minutes="0"+minutes;
            }
            String str=hours+":"+minutes;
            logger.info(fName+".output="+str);
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getUrlTextString(String text,String url) {
        String fName="getUrlTextString.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String str="["+text+"]"+"("+url+")";
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getUserMentionString(long id) {
        String fName="getUserMentionString.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String str="<@"+id+">";
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String getTextChannelMentionString(long id) {
        String fName="getTextChannelMentionString.";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            String str="<#"+id+">";
            return str;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static String listListString(List<String>list){

        String fName = "[listListString]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            return lsStringUsefullFunctions.listList2String(list);
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }

    static String[] RemoveFirstElement4ItemsArg(String[] items){
        String fName = "[RemoveFirstElement4ItemsArg]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            logger.info(fName+"items.length="+items.length);
            String [] str=new String[items.length-1];
            for(int i=1;i<items.length;i++){
                str[i-1]=items[i];
            }
            return str;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }

    static String removePrefix(String message, SelfUser selfUser){
        String fName = "[removePrefix]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            logger.info(fName + "in="+message);
            if(message.startsWith(selfUser.getAsMention().replaceFirst("<@","<@!"))){
                message=message.replaceFirst(selfUser.getAsMention().replaceFirst("<@","<@!"),"").trim();
            }
            else if(message.startsWith(selfUser.getAsMention())){
                message=message.replaceFirst(selfUser.getAsMention(),"").trim();
            }
            else if(message.startsWith(llPrefixStr)){
                message=message.replaceFirst(llPrefixStr,"").trim();
            }
            logger.info(fName + "out="+message);
            return message;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return message;}
    }
    static boolean hasPrefix(String message, SelfUser selfUser){
        String fName = "[removePrefix]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            logger.info(fName + "message="+message);
            if(message.startsWith(selfUser.getAsMention().replaceFirst("<@","<@!"))){
                logger.info(fName + "return !mention=true");
                return true;
            }
            else if(message.startsWith(selfUser.getAsMention())){
                logger.info(fName + "return mention=true");
                return true;
            }
            else if(message.startsWith(llPrefixStr)){
                logger.info(fName + "return prefix=true");
                return true;
            }
            logger.info(fName + "return default=false");
            return false;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }
    static String removePrefix(String message, Member selfMember){
        String fName = "[removePrefix]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            logger.info(fName + "in="+message);
            if(message.startsWith(selfMember.getAsMention().replaceFirst("<@","<@!"))){
                message=message.replaceFirst(selfMember.getAsMention().replaceFirst("<@","<@!"),"").trim();
            }
            else if(message.startsWith(selfMember.getAsMention())){
                message=message.replaceFirst(selfMember.getAsMention(),"").trim();
            }
            else if(message.startsWith(llPrefixStr)){
                message=message.replaceFirst(llPrefixStr,"").trim();
            }
            logger.info(fName + "out="+message);
            return message;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return message;}
    }
    static boolean hasPrefix(String message, Member selfMember){
        String fName = "[removePrefix]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try{
            logger.info(fName + "message="+message);
            if(message.startsWith(selfMember.getAsMention().replaceFirst("<@","<@!"))){
                logger.info(fName + "return !mention=true");
                return true;
            }
            else if(message.startsWith(selfMember.getAsMention())){
                logger.info(fName + "return mention=true");
                return true;
            }
            else if(message.startsWith(llPrefixStr)){
                logger.info(fName + "return prefix=true");
                return true;
            }
            logger.info(fName + "return default=false");
            return false;
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return false;}
    }

    static Emoji getEmojifromMarkdown(JSONObject jsonObject){
        String fName="[getEmojifromMarkdown]";Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            Emoji emoji=Emoji.fromMarkdown("<:"+jsonObject.getString(llCommonKeys.lsGetEmojiObjectJsonKeys.keyName)+":"+jsonObject.getString(llCommonKeys.lsGetEmojiObjectJsonKeys.keyId)+">");
            logger.info(fName+".value="+jsonObject.toString());
            return  emoji;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static boolean setThreadName4Display(Thread thread, String name) {
        String fName = "[setThreadName4Display]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if (name == null) {
                throw new Exception("Name cant be null!");
            }
            if (name.isBlank()) {
                throw new Exception("Name cant be blank!");
            }
            if (thread == null) {
                throw new Exception("Thread cant be null!");
            }
            String old=thread.getName();
            logger.info(fName + ".old thread=" + old+", new="+name);
            thread.setName(name+" ["+old+"]");
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getThreadName(Thread thread) {
        String fName = "[getThreadName]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if (thread == null) {
                throw new Exception("Thread cant be null!");
            }
            String name=thread.getName();
            logger.info(fName + ".name=" + name);
            return name;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Long getThreadId(Thread thread) {
        String fName = "[getThreadId]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            if (thread == null) {
                throw new Exception("Thread cant be null!");
            }
            long id=thread.getId();
            logger.info(fName + ".id=" + id);
            return id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    static boolean setThreadName4Display( String name) {
        String fName = "[setThreadName4Display]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            return setThreadName4Display(Thread.currentThread(),name);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String getThreadName() {
        String fName = "[getThreadName]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            return getThreadName(Thread.currentThread());
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Long getThreadId() {
        String fName = "[getThreadId]";
        Logger logger = Logger.getLogger(lsUsefullFunctions.class);
        try {
            return getThreadId(Thread.currentThread());
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
}
