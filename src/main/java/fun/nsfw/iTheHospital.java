package fun.nsfw;

import models.lc.json.profile.lcJSONUserProfile;
import kong.unirest.json.JSONObject;
import models.lc.emotes.lcEmote;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

import java.util.Iterator;

public interface iTheHospital {
    String iUrl_TakeOfUnderwear="https://d.facdn.net/art/saiandthefamilyscone/1490810697/1490810697.saiandthefamilyscone_1490742372.vallhund_com-saiandthefamilyscone_sai_wu02.png";
    String iSource_TakeOfUnderwear="https://www.furaffinity.net/view/23046538/";
    String iUrl_Injured="https://d.facdn.net/art/mxjet/1579045068/1579045068.mxjet_mxjet_hospital.jpg";
    String iSource_Injured="https://www.furaffinity.net/view/34609262/";
    String iUrl_ForcedUndressing="https://d.facdn.net/art/vir-no-vigoratus/1448754397/1448754397.vir-no-vigoratus_commission_sai_long_term_stay.png";
    String iSource_ForcedUndressing="https://www.furaffinity.net/view/18366903/";
    String iUrl_DruggedAndIsolated="https://d.facdn.net/art/sefhighwind/1433553191/1433553191.sefhighwind_sefcomm.png";
    String iSource_DruggedAndIsolated="https://www.furaffinity.net/view/16752650/";
    String iUrl_Isolated="https://d.facdn.net/art/kemira/1557258493/1557258493.kemira_f4e5c086-55d1-4c53-a55d-38c90c1e7dd7.png";
    String iSource_Isolated="https://www.furaffinity.net/view/31457525/";
    String iUrl_HeavyIsolated="https://d.facdn.net/art/kemira/1555102113/1555102113.kemira_1078b403-1cf0-4aec-9b0e-d433af3312bb.png";
    String iSource_HeavyIsolated="https://www.furaffinity.net/view/31163998/";
    String iUrl_Pet="https://d.facdn.net/art/vir-no-vigoratus/1564694794/1564694794.vir-no-vigoratus_commission_keplin_dusk_pupcage.png";
    String iSource_Pet="https://www.furaffinity.net/view/32496131/";
    String iUrl_SuitingUp="https://d.facdn.net/art/vir-no-vigoratus/1557870723/1557870723.vir-no-vigoratus_commission_commission_vanen_doktor.png";
    String iSource_SuitingUp="https://www.furaffinity.net/view/31538524/";
    String iUrl_HeavyIsolated4StaffAsault="https://d.facdn.net/art/zerofenrir/1574601484/1574463963.zerofenrir_cell_2000px.png";
    String iSource_HeavyIsolated4StaffAsault="https://www.furaffinity.net/view/33934880/";

    default JSONObject iMainBranche(){
        JSONObject json=new JSONObject();
        json.put("100",iChoice_Start());
        json.put("101",iChoice_Desk());
        return json;
    }
    default JSONObject iChoice_Start(){
        JSONObject json=new JSONObject();
        json.put("text","%person enters the reception. The air feels much colder than outside and much chemical do to the familiar scent of disinfectant. \n" +
                "%person takes a few moment to think");
        JSONObject choices=new JSONObject();
        choices.put("1",101);
        choices.put("2",102);
        choices.put("3",103);
        choices.put("4",104);
        json.put("choices",choices);
        JSONObject choicesStr=new JSONObject();
        choicesStr.put("1","Go to the desk and ask the nurse about the appointment.");
        choicesStr.put("2","Go to the vending machine and grab something to drink.");
        choicesStr.put("3","Go to the bathroom.");
        choicesStr.put("4","Take a sit and try to take a bit of a nap.");
        json.put("choicesStr",choicesStr);
        return json;
    }
    default JSONObject iChoice_Desk(){
        JSONObject json=new JSONObject();
        json.put("text","%person walks to the desk to ask the nurse. “Hi, I’m %person and wonder if the doctor is ready.” The nurse looks at you a bit annoyed, maybe they having a bad day, but they pick up the phone and call somebody. You dont listen what she is talking on the phone, but after 2-3 minutes they put it down and inform you they ready to see you in room 231 at East Wing.");
        JSONObject choices=new JSONObject();
        choices.put("1",101);
        choices.put("2",102);
        choices.put("3",103);
        choices.put("4",104);
        json.put("choices",choices);
        return json;
    }

    default String getSceneWithUser(String scene, User user){
        return scene.replaceAll("%person",user.getName()).replaceAll("%mention",user.getAsMention());
    }
    String nFieldCurrent="current", nLevel="level", nMessageId="messageId",nChannelId="channelId", nFieldLogs="logs", nOptions="options", nChoices="choices";
    String nOptionStart="start";
    default lcJSONUserProfile iInitProfile(lcJSONUserProfile gUserProfile){
        //String fName="[iInitProfile]";
        String field=nFieldCurrent;
        gUserProfile.safetyCreateFieldEntry(field);
        gUserProfile.safetyPutFieldEntry(field,nChoices,new JSONObject());
        gUserProfile.safetyPutFieldEntry(field,nOptions,new JSONObject());
        gUserProfile.safetyPutFieldEntry(field,nLevel,0);
        gUserProfile.safetyPutFieldEntry(field,nMessageId,0);
        gUserProfile.safetyPutFieldEntry(field,nChannelId,0);
        field=nFieldLogs;
        gUserProfile.safetyCreateFieldEntry(field);
        gUserProfile.safetyPutFieldEntry(field,new JSONObject());
        return  gUserProfile;
    }
    default lcJSONUserProfile setOption(lcJSONUserProfile gUserProfile,String name, boolean value){
        String fName = "[setOption]";
        if(!gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nOptions)){ ;
            gUserProfile.jsonObject.getJSONObject(nFieldCurrent).put(nOptions,new JSONObject());
        }
        gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getJSONObject(nOptions).put(name,value);
        return gUserProfile;
    }
    default boolean getOptionBoolean(lcJSONUserProfile gUserProfile,String name){
        String fName = "[getOptionBoolean]";
        if(!gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nOptions)){
            return false;
        }
        if(!gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getJSONObject(nOptions).has(name)){
            return false;
        }
        boolean result=gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getJSONObject(nOptions).getBoolean(name);
        return result;
    }
    default int getLevel(lcJSONUserProfile gUserProfile){
        if(!gUserProfile.jsonObject.has(nFieldCurrent)){return 0;}
        if(!gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nLevel)){return 0;}
        return gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getInt(nLevel);
    }
    default long getChannelId(lcJSONUserProfile gUserProfile){
        if(!gUserProfile.jsonObject.has(nFieldCurrent)){return 0;}
        if(!gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nChannelId)){return 0;}
        return gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getLong(nChannelId);
    }
    default long getMessageId(lcJSONUserProfile gUserProfile){
        if(!gUserProfile.jsonObject.has(nFieldCurrent)){return 0;}
        if(!gUserProfile.jsonObject.getJSONObject(nFieldCurrent).has(nMessageId)){return 0;}
        return gUserProfile.jsonObject.getJSONObject(nFieldCurrent).getLong(nMessageId);
    }
    default Emote getEmote(Guild guild, String key){
        //:collaryellow::collarred::collarpurple::collarpink::collarorange::collarlblue::collargreen:
        lcEmote emote=new lcEmote(guild);
        switch (key){
            case "one": emote.getEmoteByName("collaryellow");break;
            case "two": emote.getEmoteByName("collarred");break;
            case "three": emote.getEmoteByName("collarpurple");break;
            case "four": emote.getEmoteByName("collarpink");break;
            case "five": emote.getEmoteByName("collarorange");break;
            case "six": emote.getEmoteByName("collarlblue");break;
            case "seven": emote.getEmoteByName("collargreen");break;
            case "1": emote.getEmoteByName("collaryellow");break;
            case "2": emote.getEmoteByName("collarred");break;
            case "3": emote.getEmoteByName("collarpurple");break;
            case "4": emote.getEmoteByName("collarpink");break;
            case "5": emote.getEmoteByName("collarorange");break;
            case "6": emote.getEmoteByName("collarlblue");break;
            case "7": emote.getEmoteByName("collargreen");break;
        }
        return emote.getEmote();
    }
    default int getEmoteNumber(String name){
        //:collaryellow::collarred::collarpurple::collarpink::collarorange::collarlblue::collargreen:
        if(name.toLowerCase().contains("collaryellow")){return 1;}
        if(name.toLowerCase().contains("ollarred")){return 2;}
        if(name.toLowerCase().contains("collarpurple")){return 3;}
        if(name.toLowerCase().contains("collarpink")){return 4;}
        if(name.toLowerCase().contains("collarorange")){return 5;}
        if(name.toLowerCase().contains("collarlblue")){return 6;}
        if(name.toLowerCase().contains("collargreen")){return 7;}
        return 0;
    }
    default int getEmoteOption(JSONObject choices,int slot){
        if(choices.isEmpty()){return -1;}
        if(!choices.has(String.valueOf(slot))){return -2;}
        if(choices.isNull(String.valueOf(slot))){return -3;}
        return choices.getInt((String.valueOf(slot)));
    }
    default String addChoicesFields(Guild guild,JSONObject gSelectedJson){
        JSONObject gSelectedChoice=new JSONObject(),gSelectedChoiceStr=new JSONObject();
        if(gSelectedJson.has("choices")){
            gSelectedChoice=gSelectedJson.getJSONObject("choices");
        }
        if(gSelectedJson.has("choicesStr")){
            gSelectedChoiceStr=gSelectedJson.getJSONObject("choicesStr");
        }
        Iterator<String> keys=gSelectedChoice.keys();
        String desc="n/a";
        while(keys.hasNext()){
            String key=keys.next();
            if(gSelectedChoiceStr.has(key)){
                String text=gSelectedChoiceStr.getString(key);
                Emote emote=getEmote(guild,key);
                if(emote!=null){
                    text=emote.getAsMention()+" "+text;
                }
                if(desc.equalsIgnoreCase("n/a")){
                    desc=text;
                }else{
                    desc+="\n"+text;
                }
            }
        }
        return desc;
    }
}
