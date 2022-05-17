package social;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import models.ls.lsStreamHelper;
import models.ls.lsUsefullFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lcSocialization implements llMessageHelper, llGlobalHelper, llMemberHelper {
    Logger logger = Logger.getLogger(getClass());
    TextChannel gTextChannel; Member gMember, gTarget;
    public lcSocialization(TextChannel textChannel, Member author, Member target){
        String fName="[constructor]";
        logger.info(fName);
        gTextChannel=textChannel;gMember=author;gTarget=target;
    }
    String localMainPath ="resources/image/";
    String urlMainPath ="https://redhusky.000webhostapp.com/Discord/media/";
    public void sendResponse(String tag){
        String fName="[sendResponse]";
        try{
            logger.info(fName);
            logger.info(fName+".tag="+tag);
            String localPath = localMainPath +tag;
            logger.info(fName+".localPath="+localPath);
            String fileName=getFileName(localPath);
            logger.info(fName+".fileName="+fileName);
            if(fileName==null||fileName.isEmpty()||fileName.isBlank()){
                logger.error(fName+".fileName is null");
                return;
            }

            String urlPath =urlMainPath+tag+"/"+fileName;
            logger.info(fName+".urlPath="+urlPath);
            //InputStream image=getImage(urlPath);

            String str="", source="";
            logger.info(fName+" localPath="+localPath);
            logger.info(fName+" fileName="+fileName);

            JSONObject jsonObject=getJsonText(localPath,fileName);
            try{
                if(jsonObject!=null&&!jsonObject.isEmpty()&&jsonObject.has(keyText)&&!jsonObject.isNull(keyText)){
                    str=jsonObject.getString(keyText);
                }
                if(jsonObject!=null&&!jsonObject.isEmpty()&&jsonObject.has(keySource)&&!jsonObject.isNull(keySource)){
                    source=jsonObject.getString(keySource);
                }
            }catch(Exception e){
                logger.error(fName+".exception=" + e);
                logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            }


            if(str==null||str.isBlank()||str.isEmpty()){
                str=getBasicText(localPath);
            }

            logger.info(fName+" str="+str);
            str=str.replaceAll("%author",gMember.getAsMention()).replaceAll("%target",gTarget.getAsMention());
            EmbedBuilder embed=new EmbedBuilder();
            embed.setDescription(str);
            if(source!=null&&!source.isEmpty()&&!source.isBlank()){
                embed.addField("Source",source,false);
            }
            //if(image!=null){
            embed.setImage(urlPath);
            //}
            embed.setColor(llColorPurple1);
            llSendMessage(gTextChannel,embed);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessage(gTextChannel,"Error",e.toString(),llColorRed);
        }
    }
    String keySource="source", keyText="text";
    public String getFileName(String localPath){
        String fName="[getFileName]";
        try{
            logger.info(fName);
            logger.info(fName+".localPath="+localPath);
            InputStream text;
            text = new FileInputStream(localPath +"/file.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(text));
            String str = "";
            List<String> textLines=new ArrayList<>();
            if (text != null) {
                while ((str = reader.readLine()) != null) {
                    textLines.add(str);
                }
            }
            logger.info(fName+".textLines="+textLines);
            int size=textLines.size();
            logger.info(fName+".size="+size);
            int i= lsUsefullFunctions.getRandom(size);
            logger.info(fName+".i="+i);
            str=textLines.get(i);
            logger.info(fName+".str="+str);
            return  str;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            //llSendQuickEmbedMessage(gTextChannel,"Error",e.toString(),llColorRed);
            return  null;
        }
    }
    public InputStream getImage(String url){
        String fName="[getImage]";
        try{
            logger.info(fName);
            InputStream inputStream= lsStreamHelper.llGetInputStream4WebFile(url);
            if(inputStream==null){return  null;}
            logger.info(fName+".available=" + inputStream.available());
            return inputStream;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            llSendQuickEmbedMessage(gTextChannel,"Error",e.toString(),llColorRed);
            return  null;
        }
    }
    public String getBasicText(String localPath){
        String fName="[getBasicText]";
        try{
            logger.info(fName);
            logger.info(fName+" localPath="+localPath);
            InputStream text;
            text = new FileInputStream(localPath +"/text.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(text));
            String str = "";
            List<String>textLines=new ArrayList<>();
            if (text != null) {
                while ((str = reader.readLine()) != null) {
                    textLines.add(str);
                }
            }
            logger.info(fName+".textLines="+textLines);
            int size=textLines.size();
            logger.info(fName+".size="+size);
            int i=lsUsefullFunctions.getRandom(size);
            logger.info(fName+".i="+i);
            str=textLines.get(i);
            logger.info(fName+".str="+str);
            return  str;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            //llSendQuickEmbedMessage(gTextChannel,"Error",e.toString(),llColorRed);
            return  null;
        }
    }
    public JSONObject getJsonText(String localPath, String fileName){
        String fName="[getJsonText]";
        try{
            logger.info(fName);
            logger.info(fName+" localPath="+localPath);
            logger.info(fName+" fileName="+fileName);
            InputStream text;
            text = new FileInputStream(localPath +"/json.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(text));
            String str = "";
            List<String>textLines=new ArrayList<>();
            StringBuilder buff= new StringBuilder();
            if (text != null) {
                while ((str = reader.readLine()) != null) {
                    textLines.add(str);
                    buff.append(str);
                }
            }
            JSONObject jsonObject=new JSONObject(buff.toString());
            logger.info(fName+".jsonObject="+jsonObject);
            if(jsonObject.isEmpty()){
                logger.info(fName+".json is empty");
                return  null;
            }
            if(!jsonObject.has(fileName)){
                logger.info(fName+".json has no such filename");
                return  null;
            }
            if(jsonObject.isNull(fileName)){
                logger.info(fName+".json filename is null");
                return  null;
            }
            logger.info(fName+".json filename found");
            return  jsonObject.getJSONObject(fileName);
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            //llSendQuickEmbedMessage(gTextChannel,"Error",e.toString(),llColorRed);
            return  null;
        }
    }

}
