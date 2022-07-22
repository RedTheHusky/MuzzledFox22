package forRemoval.social;

import kong.unirest.json.JSONArray;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface llSocialization {
    String keyText="text", keySrc="src",keyPage="page";
    default JSONArray llGetJsonArray4Text(String localPath, String fileName){
        String fName="[llGetJsonArray4Text]";
        Logger logger = Logger.getLogger(getClass());
        try{
            logger.info(fName);
            logger.info(fName+" localPath="+localPath);
            logger.info(fName+" fileName="+fileName);
            InputStream text;
            text = new FileInputStream(localPath +"/"+fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(text));
            String str = "";
            List<String> textLines=new ArrayList<>();
            StringBuilder buff= new StringBuilder();
            if (text != null) {
                while ((str = reader.readLine()) != null) {
                    textLines.add(str);
                    buff.append(str);
                }
            }
            JSONArray jsonArray=new JSONArray(buff.toString());
            return  jsonArray;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            //llSendQuickEmbedMessage(gTextChannel,"Error",e.toString(),llColorRed);
            return  new JSONArray();
        }
    }
}
