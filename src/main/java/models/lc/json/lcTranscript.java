package models.lc.json;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;

public class lcTranscript {
    Logger logger = Logger.getLogger(getClass());
    lcText2Json TEXT2JSON=new lcText2Json();
    public lcTranscript(){
        String fName="[constructor1]";
        logger.info(fName + "");
    }

    public boolean readFile(String filePath){
        String fName="[readFile]";
        try{
            logger.info(fName);
            File file;
            InputStream fileStream=null;
            logger.info(fName+"path="+filePath);
            file=new File(filePath);
            if(file.exists()){
                fileStream = new FileInputStream(file);
            }
            if(fileStream!=null){
                if(!TEXT2JSON.isInputStream2Json(fileStream)){
                    logger.warn(fName+".failed to load");
                    return false;
                }else{
                    logger.info(fName+".loaded from file");
                }
            }
            if(TEXT2JSON.jsonArray.isEmpty()&&TEXT2JSON.jsonObject.isEmpty()){
                logger.warn(fName+".both Empty");return false;
            }
            else if(TEXT2JSON.jsonArray.isEmpty()){
                logger.warn(fName+".only array empty");return false;
            }
            else if(TEXT2JSON.jsonObject.isEmpty()){
                logger.warn(fName+".only object empty");return true;
            }
            logger.info(fName+"none empty>can't be, paradox");
            return false;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static String keyName="name",keyText="text";
    public String getText(int i){
        String fName="[getText]";
        try{
            logger.info(fName+"i="+i);
            logger.info(fName+"length="+TEXT2JSON.jsonArray.length());
            JSONObject jsonObject=TEXT2JSON.jsonArray.getJSONObject(i);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            String text=jsonObject.getString(keyText);
            logger.info(fName+"text="+text);
            return  text;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String getText(String name){
        String fName="[getText]";
        try{
            logger.info(fName+"name="+name);
            logger.info(fName+"length="+TEXT2JSON.jsonArray.length());
            for(int i=0;i<TEXT2JSON.jsonArray.length();i++){
                JSONObject jsonObject=TEXT2JSON.jsonArray.getJSONObject(i);
                logger.info(fName+"jsonObject["+i+"]="+jsonObject.toString());
                if(jsonObject.getString(keyName).equalsIgnoreCase(name)){
                    String text=jsonObject.getString(keyText);
                    logger.info(fName+"text="+text);
                    return text;
                }
            }
            return  "";
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public JSONObject getJSON(int i){
        String fName="[getJSON]";
        try{
            logger.info(fName+"i="+i);
            logger.info(fName+"length="+TEXT2JSON.jsonArray.length());
            JSONObject jsonObject=TEXT2JSON.jsonArray.getJSONObject(i);
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return  jsonObject;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public JSONObject getJSON(String name){
        String fName="[getJSON]";
        try{
            logger.info(fName+"name="+name);
            logger.info(fName+"length="+TEXT2JSON.jsonArray.length());
            for(int i=0;i<TEXT2JSON.jsonArray.length();i++){
                JSONObject jsonObject=TEXT2JSON.jsonArray.getJSONObject(i);
                logger.info(fName+"jsonObject["+i+"]="+jsonObject.toString());
                if(jsonObject.getString(keyName).equalsIgnoreCase(name)){
                    return jsonObject;
                }
            }
            return  null;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public String getTextWithMention(String name, Member author, Member target){
        String fName="[getTextWithMention]";
        try{
            logger.info(fName+"name="+name);
            return  getTextWithMention(name,author.getUser(), target.getUser());
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String getTextWithMention(String name, User author, User target){
        String fName="[getTextWithMention]";
        try{
            logger.info(fName+"name="+name);
            String text=getText(name);
            logger.info(fName+"textBefore="+text);
            text=text.replaceAll("!USER",author.getAsMention()).replaceAll("!user",author.getAsMention());
            text=text.replaceAll("!AUTHOR",author.getAsMention()).replaceAll("!author",author.getAsMention());
            text=text.replaceAll("!TARGET",target.getAsMention()).replaceAll("!target",target.getAsMention());
            logger.info(fName+"textAfter="+text);
            return  text;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String getTextWithMention(String name, Member author, Member target, TextChannel textChannel){
        String fName="[getTextWithMention]";
        try{
            logger.info(fName+"name="+name);
            return  getTextWithMention(name,author.getUser(), target.getUser(),textChannel);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
    public String getTextWithMention(String name, User author, User target, TextChannel textChannel){
        String fName="[getTextWithMention]";
        try{
            logger.info(fName+"name="+name);
            String text=getText(name);
            logger.info(fName+"textBefore="+text);
            text=text.replaceAll("!USER",author.getAsMention()).replaceAll("!user",author.getAsMention());
            text=text.replaceAll("!AUTHOR",author.getAsMention()).replaceAll("!author",author.getAsMention());
            text=text.replaceAll("!TARGET",target.getAsMention()).replaceAll("!target",target.getAsMention());
            text=text.replaceAll("!CHANNEL",target.getAsMention()).replaceAll("!channel",target.getAsMention());
            text=text.replaceAll("!TEXTCHANNEL",textChannel.getAsMention()).replaceAll("!TEXTCHANNEL",textChannel.getAsMention());
            logger.info(fName+"textAfter="+text);
            return  text;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  "";
        }
    }
}
