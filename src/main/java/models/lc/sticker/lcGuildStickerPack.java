package models.lc.sticker;

import kong.unirest.*;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.lcTempZipFile;
import models.ll.llCommonKeys;
import models.ls.lsStringUsefullFunctions;
import models.ls.lsUserHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static models.llGlobalHelper.llBotToken;
import static models.ls.lsDiscordApi.liGetGuildStickers;

public class lcGuildStickerPack {
    protected Logger logger = Logger.getLogger(getClass());
    protected Guild guild=null; String guild_id="";
    protected List<lcGuildSticker> stickers=new ArrayList<>();
    public lcGuildStickerPack() {

    }
    public lcGuildStickerPack(Guild guild) {
        String fName = "build";
        try {
            if(setGuild(guild)) {
                getPackHttp(guild.getIdLong());
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private lcGuildStickerPack(long guild) {
        String fName = "build";
        try {
            if(setGuild(guild)){
                getPackHttp(guild);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildStickerPack(long guild,List<JDA>jdas) {
        String fName = "build";
        try {
            if(setGuild(guild,jdas)){
                getPackHttp(guild);
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public lcGuildStickerPack(String guild,List<JDA>jdas) {
        String fName = "build";
        try {
            if(setGuild(Long.valueOf(guild),jdas)){
                getPackHttp(Long.valueOf(guild));
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    private boolean setGuild(Guild guild) {
        String fName = "[setGuild]";
        try {
            logger.info(fName+"guild="+guild.getId());
            this.guild=guild;
            this.guild_id=guild.getId();
            return  true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setGuild(long id,List<JDA>jdas) {
        String fName = "[setGuild]";
        try {
            logger.info(fName+"id="+id);
            for(JDA jda:jdas){
                try{
                    Guild guild=jda.getGuildById(id);
                    if(guild!=null){
                        this.guild=guild;
                        this.guild_id=this.guild.getId();
                        return true;
                    }
                }catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean setGuild(long id) {
        String fName = "[setGuild]";
        try {
            logger.info(fName+"id="+id);
            this.guild_id=String.valueOf(id);
            return false;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean getPackHttp(long id) {
        String fName = "[getPackHttp]";
        try {
            logger.info(fName+"id="+id);
            Unirest a= new Unirest();
            a.config().verifySsl(false);
            String url=liGetGuildStickers;
            url=url.replaceAll("!GUILD", String.valueOf(id));
            logger.info(fName+"url="+url);
            HttpResponse<JsonNode> response =a.get(url)
                    .header("Authorization", "Bot "+llBotToken)
                    .header("Content-Type", "application/json")
                    .asJson();
            logger.info(fName+"response.status="+response.getStatus());
            if(response.getStatus()>299){
                logger.warn("invalid status="+response.getStatus());
                return false;
            }
            JSONArray jsonArray=response.getBody().getArray();
            logger.info(fName+"response.body"+jsonArray.toString());
            String strId=String.valueOf(id);
            return  set(jsonArray);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private boolean set(JSONArray jsonArray) {
        String fName = "[set]";
        try {
            logger.info(fName+"jsonArray="+jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    lcGuildSticker sticker=null;
                    if(guild!=null){
                        sticker=new lcGuildSticker(jsonObject,guild);
                        User author=lsUserHelper.lsGetUser(guild,sticker.getAuthorIdAsLong());
                        if(author!=null){
                            sticker=new lcGuildSticker(jsonObject,guild,author);
                        }
                    }else{
                        sticker=new lcGuildSticker(jsonObject);
                    }
                    stickers.add(sticker);
                } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isEmpty() {
        String fName = "[isStickersEmpty]";
        try {
            boolean result=stickers.isEmpty();
            logger.info(fName+"result="+result);
            return result;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int size() {
        String fName = "[size]";
        try {
            int i=stickers.size();
            logger.info(fName+"size="+i);
            return i;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int countOfPng() {
        String fName = "[countOfPng]";
        try {
            int c=0;
            for(lcGuildSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.PNG)c++;
            }
            logger.info(fName + ".c=" + c);
            return c;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int countOfAPng() {
        String fName = "[countOfPng]";
        try {
            int c=0;
            for(lcGuildSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.APNG)c++;
            }
            logger.info(fName + ".c=" + c);
            return c;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int countOfLottie() {
        String fName = "[countOfPng]";
        try {
            int c=0;
            for(lcGuildSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.LOTTIE)c++;
            }
            logger.info(fName + ".c=" + c);
            return c;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    protected int countPng=-1,countApng=-1,countLottie=-1;
    public void getCountOfTypes() {
        String fName = "[getCountOfTypes]";
        try {
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return;
            }
            countPng=0;countApng=0;countLottie=0;
            for(lcGuildSticker sticker:stickers){
                if(sticker.getFormatType()== MessageSticker.StickerFormat.PNG){
                    countPng++;
                }
                if(sticker.getFormatType()== MessageSticker.StickerFormat.APNG){
                    countApng++;
                }
                if(sticker.getFormatType()== MessageSticker.StickerFormat.LOTTIE){
                    countLottie++;
                }
            }
            logger.info(fName+"countPng="+countPng+", countApng="+countApng+", countLottie="+countLottie);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public int getCountOfPng() {
        String fName = "[getCountOfPng]";
        try {
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return 0;
            }
            if(countPng==-1||countApng==-1||countLottie==-1){
                getCountOfTypes();
            }
            return countPng;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getCountOfAPng() {
        String fName = "[getCountOfAPng]";
        try {
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return 0;
            }
            if(countPng==-1||countApng==-1||countLottie==-1){
                getCountOfTypes();
            }
            return countApng;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getCountOfLottie() {
        String fName = "[getCountOfLottie]";
        try {
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return 0;
            }
            if(countPng==-1||countApng==-1||countLottie==-1){
                getCountOfTypes();
            }
            return countLottie;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public List<lcGuildSticker> getStickers() {
        String fName = "[getStickers]";
        try {
            logger.info(fName+"all");
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return stickers;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildSticker getSticker(long id) {
        String fName = "[getSticker]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            for(lcGuildSticker sticker:stickers){
                if(sticker.getIdAsLong()==id){
                    logger.info(fName+"found");
                    return sticker;
                }
            }
            logger.warn(fName+"not found");
            return null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int getStickerIndex(long id) {
        String fName = "[getSticker]";
        try {
            logger.info(fName+"id="+id);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return -1;
            }
            for(int i=0;i<stickers.size();i++){
                lcGuildSticker sticker=stickers.get(i);
                if(sticker.getIdAsLong()==id){
                    logger.info(fName+"found at="+i);
                    return i;
                }
            }
            logger.warn(fName+"not found");
            return -1;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public lcGuildSticker getSticker(int index) {
        String fName = "[getSticker]";
        try {
            logger.info(fName+"index="+index);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            return stickers.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONArray getStickersAsJson() {
        String fName = "[getStickers]";
        try {
            logger.info(fName+"all");
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return new JSONArray();
            }
            JSONArray jsonArray=new JSONArray();
            for(lcGuildSticker sticker:stickers){
                JSONObject jsonObject=sticker.getJSON();
                jsonArray.put(jsonObject);
            }
            logger.info(fName+"jsonArray="+jsonArray.toString());
            return jsonArray;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONArray();
        }
    }
    public JSONObject getStickerAsJson(int index) {
        String fName = "[getSticker]";
        try {
            logger.info(fName+"index="+index);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return new JSONObject();
            }
            lcGuildSticker sticker=stickers.get(index);
            JSONObject jsonObject=sticker.getJSON();
            logger.info(fName+"jsonObject="+jsonObject.toString());
            return jsonObject;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public String getGuildId() {
        String fName = "[getGuildId]";
        try {
            logger.info(fName+"value="+guild_id);
            if(guild_id==null)return "";
            return guild_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public long getGuildIdLong() {
        String fName = "[getGuildIdLong]";
        try {
            logger.info(fName+"value="+guild_id);
            return Long.parseLong(guild_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            return guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean hasGuild() {
        String fName = "[hasGuild]";
        try {
            return guild!=null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public lcTempZipFile getZip_oldv1() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            lcTempZipFile zipFile=new lcTempZipFile();
            int countPng=0,countApng=0,countLottie=0;
            int countUnavailable=0,countPngUnavailable=0,countApngUnavailable=0,countLottieUnavailable=0;
            if(stickers==null)stickers=new ArrayList<>();
            for(int i=0;i<stickers.size();i++){
                try {
                    lcGuildSticker sticker= stickers.get(i);
                    logger.info(fName+"stickers["+i+"]:id="+sticker.getId());
                    zipFile.addEntity(sticker.getId()+".json",sticker.getJSON());
                    String id=sticker.getId();
                    switch (sticker.getFormatType()){
                        case LOTTIE:
                            InputStream inputStream_Lottie=sticker.getLottieStream();
                            if(inputStream_Lottie!=null){
                                zipFile.addEntity(id+".lottie",inputStream_Lottie);
                                inputStream_Lottie.close();
                            }
                            countLottie++;
                            if(!sticker.isAvailable()){
                                countUnavailable++;
                                countLottieUnavailable++;
                            }
                            break;
                        case PNG: case APNG:
                            if(sticker.getFormatType()== MessageSticker.StickerFormat.APNG){
                                countApng++;
                                if(!sticker.isAvailable()){
                                    countUnavailable++;
                                    countApngUnavailable++;
                                }
                            }else{
                                countPng++;
                                if(!sticker.isAvailable()){
                                    countUnavailable++;
                                    countPngUnavailable++;
                                }
                            }
                            InputStream inputStream32=sticker.getMediaStream32();
                            InputStream inputStream64=sticker.getMediaStream64();
                            InputStream inputStream128=sticker.getMediaStream128();
                            InputStream inputStream256=sticker.getMediaStream256();
                            InputStream inputStream512=sticker.getMediaStream512();
                            InputStream inputStream1024=sticker.getMediaStream1024();
                            String ext=sticker.getExtension();
                            if(inputStream32!=null){
                                zipFile.addEntity(id+"_32."+ext,inputStream32);
                            }
                            if(inputStream64!=null){
                                zipFile.addEntity(id+"_64."+ext,inputStream64);
                            }
                            if(inputStream128!=null){
                                zipFile.addEntity(id+"_128."+ext,inputStream128);
                            }
                            if(inputStream256!=null){
                                zipFile.addEntity(id+"_256."+ext,inputStream256);
                            }
                            if(inputStream512!=null){
                                zipFile.addEntity(id+"_512."+ext,inputStream512);
                            }
                            if(inputStream1024!=null){
                                zipFile.addEntity(id+"_1024."+ext,inputStream1024);
                            }
                            inputStream32.close(); inputStream64.close(); inputStream128.close();
                            inputStream256.close(); inputStream512.close(); inputStream1024.close();
                            break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            try {
                JSONObject jsonPack=new JSONObject();
                jsonPack.put(llCommonKeys.keyGuild_Id,getGuildId());
                jsonPack.put(llCommonKeys.keyCount,new JSONObject());
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("total",size());
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("png",countPng);
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("apng",countApng);
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("lottie",countLottie);
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("unavailable",new JSONObject());
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("total",countUnavailable);
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("png",countPngUnavailable);
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("apng",countApngUnavailable);
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("lottie",countLottieUnavailable);
                if(hasGuild())jsonPack.put(llCommonKeys.keyName,getGuild().getName());
                logger.info(fName+"pack="+jsonPack.toString());
                zipFile.addEntity("pack.json",jsonPack);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return zipFile;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcTempZipFile getZip() {
        String fName = "[getZip]";
        try {
            logger.info(fName);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            lcTempZipFile zipFile=new lcTempZipFile();
            int countPng=0,countApng=0,countLottie=0;
            int countUnavailable=0,countPngUnavailable=0,countApngUnavailable=0,countLottieUnavailable=0;
            if(stickers==null)stickers=new ArrayList<>();
            for(int i=0;i<stickers.size();i++){
                try {
                    lcGuildSticker sticker= stickers.get(i);
                    logger.info(fName+"stickers["+i+"]:id="+sticker.getId());
                    zipFile.addEntity(sticker.getId()+".json",sticker.getJSON());
                    String id=sticker.getId();
                    switch (sticker.getFormatType()){
                        case LOTTIE:
                            InputStream inputStream_Lottie=sticker.getLottieStream();
                            if(inputStream_Lottie!=null){
                                zipFile.addEntity(id+".lottie",inputStream_Lottie);
                                inputStream_Lottie.close();
                            }
                            countLottie++;
                            if(!sticker.isAvailable()){
                                countUnavailable++;
                                countLottieUnavailable++;
                            }
                            break;
                        case PNG: case APNG:
                            String firstFileName="";
                            if(sticker.getFormatType()== MessageSticker.StickerFormat.APNG){
                                firstFileName="a_";
                                countApng++;
                                if(!sticker.isAvailable()){
                                    countUnavailable++;
                                    countApngUnavailable++;
                                }
                            }else{
                                countPng++;
                                if(!sticker.isAvailable()){
                                    countUnavailable++;
                                    countPngUnavailable++;
                                }
                            }
                            InputStream inputStream32=sticker.getMediaStream32();
                            InputStream inputStream64=sticker.getMediaStream64();
                            InputStream inputStream128=sticker.getMediaStream128();
                            InputStream inputStream256=sticker.getMediaStream256();
                            InputStream inputStream512=sticker.getMediaStream512();
                            InputStream inputStream1024=sticker.getMediaStream1024();
                            String ext="png";
                            if(inputStream32!=null){
                                zipFile.addEntity(firstFileName+id+"_32."+ext,inputStream32);
                            }
                            if(inputStream64!=null){
                                zipFile.addEntity(firstFileName+id+"_64."+ext,inputStream64);
                            }
                            if(inputStream128!=null){
                                zipFile.addEntity(firstFileName+id+"_128."+ext,inputStream128);
                            }
                            if(inputStream256!=null){
                                zipFile.addEntity(firstFileName+id+"_256."+ext,inputStream256);
                            }
                            if(inputStream512!=null){
                                zipFile.addEntity(firstFileName+id+"_512."+ext,inputStream512);
                            }
                            if(inputStream1024!=null){
                                zipFile.addEntity(firstFileName+id+"_1024."+ext,inputStream1024);
                            }
                            inputStream32.close(); inputStream64.close(); inputStream128.close();
                            inputStream256.close(); inputStream512.close(); inputStream1024.close();
                            break;
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            try {
                JSONObject jsonPack=new JSONObject();
                jsonPack.put(llCommonKeys.keyGuild_Id,getGuildId());
                jsonPack.put(llCommonKeys.keyCount,new JSONObject());
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("total",size());
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("png",countPng);
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("apng",countApng);
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("lottie",countLottie);
                jsonPack.getJSONObject(llCommonKeys.keyCount).put("unavailable",new JSONObject());
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("total",countUnavailable);
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("png",countPngUnavailable);
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("apng",countApngUnavailable);
                jsonPack.getJSONObject(llCommonKeys.keyCount).getJSONObject("unavailable").put("lottie",countLottieUnavailable);
                if(hasGuild())jsonPack.put(llCommonKeys.keyName,getGuild().getName());
                logger.info(fName+"pack="+jsonPack.toString());
                zipFile.addEntity("pack.json",jsonPack);
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            return zipFile;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public InputStream getZipAsInputStream() {
        String fName = "[getZipAsInputStream]";
        try {
            logger.info(fName);
            if(isEmpty()){
                logger.warn(fName+"is empty");
                return null;
            }
            lcTempZipFile zipFile=getZip();
            InputStream targetStream = zipFile.getInputStream();
            return targetStream;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack updateSticker(long id,String name, String tag, String desc) {
        String fName = "[updateSticker]";
        try {
            logger.info(fName+"id="+id+", name="+name+", tag="+tag+", desc="+desc);
            lcGuildSticker sticker=getSticker(id);
            if(sticker==null){
                logger.warn(fName+"invalid sticker id");
                return null;
            }
            if(name!=null&&!name.isBlank()){
                sticker.setName(name);
            }
            if(tag!=null&&!tag.isBlank()){
                sticker.setTag(tag);
            }
            if(desc!=null){
                sticker.setDescription(tag);
            }
            if(sticker.update()==null){
                logger.warn(fName+"failed to update sticker");
                return null;
            }
            stickers.set(getStickerIndex(id),sticker);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack updateSticker(int index,String name, String tag, String desc) {
        String fName = "[updateSticker]";
        try {
            logger.info(fName+"id="+index+", name="+name+", tag="+tag+", desc="+desc);
            lcGuildSticker sticker=getSticker(index);
            if(sticker==null){
                logger.warn(fName+"invalid sticker index");
                return null;
            }
            if(name!=null&&!name.isBlank()){
                sticker.setName(name);
            }
            if(tag!=null&&!tag.isBlank()){
                sticker.setTag(tag);
            }
            if(desc!=null){
                sticker.setDescription(tag);
            }
            if(sticker.update()==null){
                logger.warn(fName+"failed to update sticker");
                return null;
            }
            stickers.set(index,sticker);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack updateSticker(long id,JSONObject jsonObject) {
        String fName = "[updateSticker]";
        try {
            logger.info(fName+"id="+id+", jsonObject="+jsonObject.toString());
            lcGuildSticker sticker=getSticker(id);
            if(sticker==null){
                logger.warn(fName+"invalid sticker id");
                return null;
            }
            if(jsonObject.has(lcGuildSticker.KEYS.keyName)){
                sticker.setName(jsonObject.optString(lcGuildSticker.KEYS.keyName));
            }
            if(jsonObject.has(lcGuildSticker.KEYS.keyTags)){
                sticker.setTag(jsonObject.optString(lcGuildSticker.KEYS.keyTags));
            }else
            if(jsonObject.has("tag")){
                sticker.setTag(jsonObject.optString("tag"));
            }
            if(jsonObject.has(lcGuildSticker.KEYS.keyDescription)){
                sticker.setDescription(jsonObject.optString(lcGuildSticker.KEYS.keyDescription));
            }
            if(sticker.update()==null){
                logger.warn(fName+"failed to update sticker");
                return null;
            }
            stickers.set(getStickerIndex(id),sticker);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack updateSticker(int index,JSONObject jsonObject) {
        String fName = "[updateSticker]";
        try {
            logger.info(fName+"id="+index+", jsonObject="+jsonObject.toString());
            lcGuildSticker sticker=getSticker(index);
            if(sticker==null){
                logger.warn(fName+"invalid sticker index");
                return null;
            }
            if(jsonObject.has(lcGuildSticker.KEYS.keyName)){
                sticker.setName(jsonObject.optString(lcGuildSticker.KEYS.keyName));
            }
            if(jsonObject.has(lcGuildSticker.KEYS.keyTags)){
                sticker.setTag(jsonObject.optString(lcGuildSticker.KEYS.keyTags));
            }else
            if(jsonObject.has("tag")){
                sticker.setTag(jsonObject.optString("tag"));
            }
            if(jsonObject.has(lcGuildSticker.KEYS.keyDescription)){
                sticker.setDescription(jsonObject.optString(lcGuildSticker.KEYS.keyDescription));
            }
            if(sticker.update()==null){
                logger.warn(fName+"failed to update sticker");
                return null;
            }
            stickers.set(index,sticker);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack addSticker(Message .Attachment attachment, String name, String tag, String desc) {
        String fName = "[addSticker]";
        try {
            logger.info(fName+"name="+name+", tag="+tag+", desc="+desc);
            if(attachment==null){
                logger.warn(fName+"can;t be null");
                return null;
            }
            lcGuildStickerUploader stickerUploader=new lcGuildStickerUploader(getGuild());
            if(stickerUploader.setFile(attachment)==null){
                logger.warn(fName+"invalid attachment type");
                return null;
            }
            if(stickerUploader.setName(name)==null){
                logger.warn(fName+"invalid name");
                return null;
            }
            if(stickerUploader.setTag(tag)==null){
                logger.warn(fName+"invalid tag");
                return null;
            }
            if(stickerUploader.setDescription(desc)==null){
                logger.warn(fName+"invalid desc");
                return null;
            }
            HttpResponse<JsonNode> response=stickerUploader.upload();
            if(response.getStatus()>299){
                logger.warn(fName+"failed to upload sticker");
                return null;
            }
            lcGuildSticker sticker=stickerUploader.getSticker();
            if(sticker==null||sticker.isEmpty()){
                logger.warn(fName+"sticker is null or empty");
                return null;
            }
            stickers.add(sticker);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack deleteSticker(long id) {
        String fName = "[deleteSticker]";
        try {
            logger.info(fName+"id="+id);
            lcGuildSticker sticker=getSticker(id);
            if(sticker==null){
                logger.warn(fName+"invalid sticker id");
                return null;
            }
            HttpResponse<JsonNode> response=sticker.delete();
            if(response.getStatus()>299){
                logger.warn(fName+"failed to upload sticker");
                return null;
            }
            int index=getStickerIndex(id);
            stickers.remove(index);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGuildStickerPack deleteSticker(int index) {
        String fName = "[deleteSticker]";
        try {
            logger.info(fName+"id="+index);
            lcGuildSticker sticker=getSticker(index);
            if(sticker==null){
                logger.warn(fName+"invalid sticker index");
                return null;
            }
            HttpResponse<JsonNode> response=sticker.delete();
            if(response.getStatus()>299){
                logger.warn(fName+"failed to upload sticker");
                return null;
            }
            stickers.remove(index);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public static class lcGuildStickerUploader {
        Logger logger = Logger.getLogger(getClass());
        protected Guild guild=null;
        protected String name="",description="",tags="";
        protected int type=0;
        public interface  TYPES{
            ContentType PNG=ContentType.IMAGE_PNG;
            ContentType APNG=ContentType.IMAGE_PNG;
            ContentType LOTTIE=ContentType.APPLICATION_JSON;
        }
        public lcGuildStickerUploader(Guild guild) {
            String fName = "build";
            try {
                setGuild(guild);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public lcGuildStickerUploader(long id, List<JDA>jdas) {
            String fName = "build";
            try {
                setGuild(id,jdas);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public lcGuildStickerUploader(Message message) {
            String fName = "build";
            try {
                setMessage(message);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public lcGuildStickerUploader(Guild guild,Message.Attachment attachment) {
            String fName = "build";
            try {
                if(setGuild(guild)){
                    setFile(attachment);
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        public lcGuildStickerUploader(long id, List<JDA>jdas,Message.Attachment attachment) {
            String fName = "build";
            try {
                if(setGuild(id,jdas)){
                    setFile(attachment);
                }
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
        }
        private boolean setGuild(Guild guild) {
            String fName = "[setGuild]";
            try {

                logger.info(fName+"guild="+guild.getId());
                this.guild=guild;
                return true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean setGuild(long id,List<JDA>jdas) {
            String fName = "[setGuild]";
            try {
                logger.info(fName+"id="+id);
                for(JDA jda:jdas){
                    try{
                        Guild guild=jda.getGuildById(id);
                        if(guild!=null){
                            this.guild=guild;
                            return true;
                        }
                    }catch (Exception e) {
                        logger.error(fName + ".exception=" + e);
                        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                        return false;
                    }
                }
                return false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        private boolean setMessage(Message message) {
            String fName = "[setMessage]";
            try {

                logger.info(fName+"message="+message.getId());
                if(!message.isFromGuild()){
                    logger.warn("message is not from guild");
                    return false;
                }
                guild=message.getGuild();
                List<Message.Attachment>attachments=message.getAttachments();
                if(attachments.isEmpty()){
                    logger.warn("message has 0 attachments");
                    return false;
                }
                return setFile(attachments.get(0))!=null;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int getType() {
            String fName = "getType";
            try {
                logger.info(fName+"value="+type);
                return type;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean hasGuild() {
            String fName = "[hasGuild]";
            try {
                if(guild==null){
                    logger.warn("guild not set");
                    return false;
                }
                return  true;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isEmpty() {
            String fName = "[isEmpty]";
            try {
                if(name==null||name.isBlank()){
                    logger.warn("name is blank");
                    return true;
                }
                if(tags==null||tags.isBlank()){
                    logger.warn("tags is blank");
                    return true;
                }
            /*if(file==null||file.isEmpty()){
                logger.warn("file is blank");
                return true;
            }*/
                if(attachment==null){
                    logger.warn("attachment is blank");
                    return true;
                }
                return  false;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return true;
            }
        }
        public boolean isImg() {
            String fName = "[isImg]";
            try {
                logger.info(fName+"value="+type);
                return type == 1 || type == 2;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public MessageSticker.StickerFormat getFormatType() {
            String fName = "[getFormat]";
            try {
                MessageSticker.StickerFormat result=MessageSticker.StickerFormat.UNKNOWN;
                switch (getType()){
                    case 1:result= MessageSticker.StickerFormat.PNG;break;
                    case 2:result= MessageSticker.StickerFormat.APNG;break;
                    case 3:result= MessageSticker.StickerFormat.LOTTIE;break;
                }
                logger.info(fName+"result="+result);
                return  result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return MessageSticker.StickerFormat.UNKNOWN;
            }
        }
        public ContentType getContentType() {
            String fName = "[getContentType]";
            try {
                ContentType result=null;
                switch (getType()){
                    case 1:result= TYPES.PNG;break;
                    case 2:result= TYPES.APNG;break;
                    case 3:result= TYPES.LOTTIE;break;
                }

                return  result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getName() {
            String fName = "getName";
            try {
                logger.info(fName+"value="+name);
                if(name==null)return "";
                return  name;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getDescription() {
            String fName = "getDescription";
            try {
                logger.info(fName+"value="+description);
                if(description==null)return "";
                return description;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public String getTag() {
            String fName = "[getTag]";
            try {
                logger.info(fName+"value="+tags);
                if(tags==null) return "";
                return  tags;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public Guild getGuild() {
            String fName = "[getGuild]";
            try {
                logger.info(fName+"value="+guild.getId());
                return guild;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getGuildId() {
            String fName = "getGuildId";
            try {
                String value=guild.getId();
                logger.info(fName+"value="+value);
                if(value==null)return "";
                return value;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
        public long getGuildIdAsLong() {
            String fName = "getGuildIdAsLong";
            try {
                return lsStringUsefullFunctions.String2Long(getGuildId());
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public JSONObject getJSON() {
            String fName = "[getJSON]";
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(lcGuildSticker.KEYS.keyName,name);
                jsonObject.put(lcGuildSticker.KEYS.keyDescription,getDescription());
                jsonObject.put(lcGuildSticker.KEYS.keyGuildId,getGuildId());
                jsonObject.put(lcGuildSticker.KEYS.keyType,getType());
                jsonObject.put(lcGuildSticker.KEYS.keyFormateType,getFormatType());
                jsonObject.put(lcGuildSticker.KEYS.keyTags,getTag());
                logger.info(fName+"jsonObject="+jsonObject.toString());
                return  jsonObject;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new JSONObject();
            }
        }
        public lcGuildStickerUploader clear() {
            String fName = "[clear]";
            try {
                description="";name="";tags="";
                //file=new lcMyMessageJsonBuilder.incFileInputStream();
                attachment=null;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcGuildStickerUploader setName(String str) {
            String fName = "[setName]";
            try {
                if(str==null){
                    logger.warn("this cant be null");
                    return null;
                }
                str=str.replaceAll(" ","");
                if(str.isBlank()){
                    logger.warn("this cant be blank");
                    return null;
                }
                name=str;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcGuildStickerUploader setDescription(String str) {
            String fName = "[setDescription]";
            try {
                if(str==null){
                    logger.warn("this cant be null");
                    return null;
                }
                str=str.replaceAll(" ","");
                description=str;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcGuildStickerUploader setTag(String str) {
            String fName = "[setTag]";
            try {
                if(str==null){
                    logger.warn("this cant be null");
                    return null;
                }
                str=str.replaceAll(" ","");
                tags=str;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        /*
        private lcGuildStickerUploader setType(int type) {
            String fName = "[setType";
            try {
                if(type<0||type>3){
                    logger.warn("invalid type");
                    return null;
                }
                this.type=type;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private lcGuildStickerUploader setType(MessageSticker.StickerFormat type) {
            String fName = "[setFormatType]";
            try {
                if(type==null){
                    logger.warn("this cant be null");
                    return null;
                }
                switch (type){
                    case PNG:this.type=1;break;
                    case APNG:this.type=2;break;
                    case LOTTIE:this.type=3;break;
                    default:this.type=0;break;
                }

                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private lcGuildStickerUploader setType(ContentType type) {
            String fName = "[setFormatType]";
            try {
                if(type==null){
                    logger.warn("this cant be null");
                    return null;
                }
                if(type==TYPES.PNG){
                    this.type=1;
                }
                else if(type==TYPES.APNG){
                    this.type=2;
                }
                else if(type==TYPES.LOTTIE){
                    this.type=3;
                }else{
                    this.type=0;
                }

                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private lcGuildStickerUploader setFile_incFileInputStream(lcMyMessageJsonBuilder.incFileInputStream file) {
            String fName = "[setFile_incFileInputStream]";
            try {
                if(file==null){
                    logger.warn("this cant be null");
                    return null;
                }
                if(file.isEmpty()){
                    logger.warn("this cant be empty");
                    return null;
                }
                if(file.getContentType()== ContentType.IMAGE_PNG)type=1;
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        private lcMyMessageJsonBuilder.incFileInputStream getFile_incFileInputStream() {
            String fName = "[getFile_incFileInputStream]";
            try {
                if(file.isEmpty()){
                    logger.warn("is empty");
                    return  null;
                }
                return  file;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        */
        //private lcMyMessageJsonBuilder.incFileInputStream file=new lcMyMessageJsonBuilder.incFileInputStream();
        protected Message.Attachment attachment=null;
        public lcGuildStickerUploader setFile(Message.Attachment attachment) {
            String fName = "[setFile]";
            try {
                if(attachment==null){
                    logger.warn("this cant be null");
                    return null;
                }
                if(attachment.isVideo()){
                    logger.warn("this cant be video");
                    return null;
                }
                String type=attachment.getContentType().toLowerCase();
                if(type.contains("apng")){
                    this.type=2;this.attachment=attachment;
                }else
                if(type.contains("png")){
                    this.type=1;this.attachment=attachment;
                }else
                if(type.contains("lottie")||type.contains("json")){
                    this.type=3;this.attachment=attachment;
                }else{
                    this.type=0;this.attachment=null;
                }
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public Message.Attachment getFile() {
            String fName = "[getFile]";
            try {
                if(attachment==null){
                    logger.warn("is empty");
                    return  null;
                }
                return  attachment;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public HttpResponse<JsonNode> upload() {
            String fName = "[upload]";
            try {
                String url = liGetGuildStickers;
                url=url.replaceAll("!GUILD", getGuildId());
                logger.info(fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> response =null;
                MultipartBody b=a.post(url)
                        .header("Authorization", "Bot "+llBotToken)
                        .header("accept", "application/json")
                        .header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundaryGKUGiidXo91BPxsO")
                        .multiPartContent();
                b.field(lcGuildSticker.KEYS.keyName,getName());
                b.field(lcGuildSticker.KEYS.keyTags,getTag());
                if(!getDescription().isBlank())b.field(lcGuildSticker.KEYS.keyDescription,getDescription());

                b.field("file",attachment.retrieveInputStream().get(),getContentType(),attachment.getFileName());
                response=b.asJson();
                logger.info(fName+".status ="+response.getStatus());
                if(response.getStatus()>299){
                    logger.warn("invalid status="+response.getStatus());
                    return response;
                }
                logger.warn("success upload");
                setSticker(response);
                return  response;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return new FailedResponse<>(e);
            }
        }
        protected lcGuildSticker guildSticker=new lcGuildSticker();
        protected void setSticker(HttpResponse<JsonNode> response) {
            String fName = "[setSticker]";
            try {
                if(response.getStatus()>299){
                    logger.warn("invalid status");
                    return;
                }
                JSONObject jsonObject=response.getBody().getObject();
                if(guild!=null){
                    guildSticker=new lcGuildSticker(jsonObject,guild);
                }

            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lcGuildSticker getSticker() {
            String fName = "[getSticker]";
            try {
                if(guildSticker.isEmpty()){
                    logger.warn("is empty");
                    return  null;
                }
                return  guildSticker;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcGuildStickerPack getStickerPack() {
            String fName = "[getSticker]";
            try {
                if(guildSticker.isEmpty()){
                    logger.warn("is empty");
                    return  null;
                }
                return  guildSticker.getStickerPack();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
}
