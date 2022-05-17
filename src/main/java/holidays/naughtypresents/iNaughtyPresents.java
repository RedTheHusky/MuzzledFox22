package holidays.naughtypresents;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.ll.llCommonKeys;
import models.ll.llCommonVariables;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface iNaughtyPresents {
    String profileName="NaughtyPresents2021",table= llGlobalHelper.llMemberProfile;
    interface KEYS_User {
        String  presents="presents",
                events="events",
                timestamp_call="timestamp_call";
        interface Present{
            String refKey ="ref_key",
                    hp ="hp",
                    timestamp_got="timestamp_got",
                    timestamp_used="timestamp_used",
                    gift="gift",
                    event="event";
                interface Gift{
                    String  mode="mode",
                            timestamp="timestamp",
                            target="target";
                }
        }
        interface Event {
            String  refKey="ref_key",
                    timestamp="timestamp";
        }
    }
    interface KEYS_Store {
        String  presents="presents",
                events="events";
        interface Present{
            String  name="name",
                    hp="hp",
                    embed_show="embed_show",
                    embed_hidden="embed_hidden",
                    embeds_use_self="embeds_use_self",
                    embeds_use_others="embeds_use_others",
                    embed_usedup="embed_usedup";
        }
        interface Event {
            String  name="name",
                    embed="embed",
                    presents="presents",
                    restraints ="restraints";
            interface Restraint{
                String level="level";
                interface Suit {
                    String type="type",matterial="matterial";
                }
                interface Sj {
                    String wear="wear",arm="arm",crotch="crotch";
                }
            }
        }
    }
    int maxPatreonEvents=1000000000, maxPatreonPresent=20, maxPatreonPresentCount=20;
    int maxEvents=50, maxPresent=10, maxPresentCount=10;
    long timeoutBetweenEvents= llCommonVariables.milliseconds_hour*6, timeoutBetweenPresentUse=llCommonVariables.milliseconds_hour*6;
    String gMainFile="resources/json/holiday/naughtypresents/main.json",
            gMainMenuFile="resources/json/holiday/naughtypresents/mainMenu.json",
            gPresentsMenuFile="resources/json/holiday/naughtypresents/presentsMenu.json";
            /*gEventsMenuFile="resources/json/holiday/naughtypresents/eventsMenu.json",

            gEventMenuFile="resources/json/holiday/naughtypresents/eventMenu.json",
            gPresentMenuFile="resources/json/holiday/naughtypresents/presentMenu.json";*/
    String gTitle="Naughty Presents",gCommand="presents";
    String comGoEvent="goevent",comGoEvent_Dm="goevent_dm",comInventory="inventory",comHelp="information_source",comForward="forward",comBack="back",comUp="up",comUse="use";
    static JSONObject newUserGift(){
        String fName="[newUserGift]";
        Logger logger = Logger.getLogger(iNaughtyPresents.class);
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.refKey,"");
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.hp,0);
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.timestamp_used,0);
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.timestamp_got,0);
            jsonObject.put(KEYS_User.Present.gift,new JSONObject());
            jsonObject.getJSONObject(KEYS_User.Present.gift).put(KEYS_User.Present.Gift.mode,0);
            jsonObject.getJSONObject(KEYS_User.Present.gift).put(KEYS_User.Present.Gift.timestamp,0);
            jsonObject.getJSONObject(KEYS_User.Present.gift).put(KEYS_User.Present.Gift.target,"");
            jsonObject.put(KEYS_User.Present.event,new JSONObject());
            jsonObject.getJSONObject(KEYS_User.Present.event).put(KEYS_User.Event.refKey,"");
            jsonObject.getJSONObject(KEYS_User.Present.event).put(KEYS_User.Event.timestamp,0);
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    static JSONObject newUserEvent(){
        String fName="[newUserEvent]";
        Logger logger = Logger.getLogger(iNaughtyPresents.class);
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(iNaughtyPresents.KEYS_User.Present.refKey,"");
            jsonObject.put(iNaughtyPresents.KEYS_User.Event.timestamp,"");
            return jsonObject;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    static EmbedBuilder buildEmbed(JSONObject jsonObject){
        String fName="[buildEmbed]";
        Logger logger = Logger.getLogger(iNaughtyPresents.class);
        try{
            EmbedBuilder embedBuilder=new EmbedBuilder();
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            Set<String> keys=jsonObject.keySet();
            for(String key:keys){
                try {
                    logger.info(fName+"key="+key);
                    if(!jsonObject.isNull(key))
                        switch (key){
                            case llCommonKeys.EmbedBuildStructure.title:
                                JSONObject jsonObject2=jsonObject.getJSONObject(key);
                                if( jsonObject2.has(llCommonKeys.EmbedBuildStructure.Title.text)
                                        &&!jsonObject2.isNull(llCommonKeys.EmbedBuildStructure.Title.text)
                                        &&!jsonObject2.optString(llCommonKeys.EmbedBuildStructure.Title.text,"").isBlank()
                                        &&jsonObject2.has(llCommonKeys.EmbedBuildStructure.Title.url)
                                        &&!jsonObject2.isNull(llCommonKeys.EmbedBuildStructure.Title.url)
                                        &&!jsonObject2.optString(llCommonKeys.EmbedBuildStructure.Title.url,"").isBlank()
                                ){
                                    embedBuilder.setTitle(jsonObject2.optString(llCommonKeys.EmbedBuildStructure.Title.text,"."),jsonObject2.optString(llCommonKeys.EmbedBuildStructure.Title.url,"."));
                                }else
                                if( jsonObject2.has(llCommonKeys.EmbedBuildStructure.Title.text)
                                        &&!jsonObject2.isNull(llCommonKeys.EmbedBuildStructure.Title.text)
                                        &&!jsonObject2.optString(llCommonKeys.EmbedBuildStructure.Title.text,"").isBlank()
                                ){
                                    embedBuilder.setTitle(jsonObject2.optString(llCommonKeys.EmbedBuildStructure.Title.text,"."));
                                }
                                break;
                            case llCommonKeys.EmbedBuildStructure.color:
                                Object object=jsonObject.get(key);
                                if(object instanceof JSONArray){
                                    JSONArray jsonArrayColor=jsonObject.getJSONArray(key);
                                    int r=0,g=0,b=0;
                                    if(jsonArrayColor.length()>=1)r=jsonArrayColor.getInt(0);
                                    if(jsonArrayColor.length()>=2)r=jsonArrayColor.getInt(1);
                                    if(jsonArrayColor.length()>=3)r=jsonArrayColor.getInt(2);
                                    Color color=new Color(r,g,b);
                                    embedBuilder.setColor(color);
                                }else
                                if(object instanceof JSONObject){

                                }else
                                if(object instanceof String){
                                    Color color=Color.decode(jsonObject.getString(key));
                                    if(color==null)color=Color.BLACK;
                                    embedBuilder.setColor(color);
                                }else
                                if(object instanceof Integer){
                                    Color color=new Color(jsonObject.getInt(key));
                                    embedBuilder.setColor(color);
                                }
                                break;
                            case llCommonKeys.EmbedBuildStructure.description:
                                embedBuilder.setDescription(jsonObject.optString(key,"."));
                                break;
                            case llCommonKeys.EmbedBuildStructure.image:
                                embedBuilder.setImage(jsonObject.optString(key,"."));
                                break;
                            case llCommonKeys.EmbedBuildStructure.thumbnail:
                                embedBuilder.setThumbnail(jsonObject.optString(key,"."));
                                break;
                            case llCommonKeys.EmbedBuildStructure.fields:
                                break;
                            case llCommonKeys.EmbedBuildStructure.footer:
                                JSONObject jsonObject3=jsonObject.getJSONObject(key);
                                if( jsonObject3.has(llCommonKeys.EmbedBuildStructure.Footer.text)
                                        &&!jsonObject3.isNull(llCommonKeys.EmbedBuildStructure.Footer.text)
                                        &&!jsonObject3.optString(llCommonKeys.EmbedBuildStructure.Footer.text,"").isBlank()
                                        &&jsonObject3.has(llCommonKeys.EmbedBuildStructure.Footer.icon_url)
                                        &&!jsonObject3.isNull(llCommonKeys.EmbedBuildStructure.Footer.icon_url)
                                        &&!jsonObject3.optString(llCommonKeys.EmbedBuildStructure.Footer.icon_url,"").isBlank()
                                ){
                                    embedBuilder.setFooter(jsonObject3.optString(llCommonKeys.EmbedBuildStructure.Footer.text,"."),jsonObject3.optString(llCommonKeys.EmbedBuildStructure.Footer.icon_url,"."));
                                }else
                                if( jsonObject3.has(llCommonKeys.EmbedBuildStructure.Footer.text)
                                        &&!jsonObject3.isNull(llCommonKeys.EmbedBuildStructure.Footer.text)
                                        &&!jsonObject3.optString(llCommonKeys.EmbedBuildStructure.Footer.text,"").isBlank()
                                ){
                                    embedBuilder.setFooter(jsonObject3.optString(llCommonKeys.EmbedBuildStructure.Footer.text,"."));
                                }
                                break;
                            case llCommonKeys.EmbedBuildStructure.author:

                                break;
                        }
                }catch (Exception e){
                    logger.error(fName+".exception=" + e);
                    logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            return embedBuilder;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }

    }
    static String convertDuration2Human(long value) {
        String fName = "[convertDuration2Human]";
        Logger logger = Logger.getLogger(iNaughtyPresents.class);
        logger.info(fName+"value="+value);
        try {
            /*int weeks = (int) (value / 604800);int days = (int) (value % 604800) / 86400;int hours = (int) ((value % 604800) % 86400) / 3600;
            String duration="";
            if(weeks>1){
                duration+=weeks+" weeks ";
            }else
            if(weeks==1){
                duration+="1 week ";
            }
            if(days>1){
                duration+=days+" days ";
            }else
            if(days==1){
                duration+="1 day ";
            }
            if(hours>1){
                duration+=hours+" hours ";
            }else
            if(hours==1){
                duration+="1 hour ";
            }
            logger.info(fName+"duration="+duration);*/
            String duration="";
            long hours=TimeUnit.MILLISECONDS.toHours(value);
            long minutes=TimeUnit.MILLISECONDS.toMinutes(value) - (hours * 60);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(value) - ((hours * 60 * 60) + (minutes * 60));
            logger.info(fName+"hours="+hours+", minutes="+minutes+", seconds="+seconds);
            if(hours==1){
                duration+="1 hour";
            }else if(hours>1){
                duration+= hours +" hours";
            }
            if(minutes>=1){
                if(!duration.isBlank())duration+=" ";
                if(minutes==1){
                    duration+="1 minute";
                }else if(minutes>1){
                    duration+= minutes +" minutes";
                }
            }
            if(seconds>=1){
                if(!duration.isBlank())duration+=" ";
                if(seconds==1){
                    duration+="1 second";
                }else if(seconds>1){
                    duration+= minutes +" seconds";
                }
            }
            logger.info(fName+"duration="+duration);
            return duration;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    boolean isPresentHidden=false;
}
