package fun.playerOrderPicker;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class entityPlayerOrderPicker {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityHood]";
    public JSONObject extraJsonObject=new JSONObject();
    public List<entityPlayer>gPlayers=new ArrayList<>();
    public List<entityPlayer>gOldPlayers=new ArrayList<>();
    public entityPlayerOrderPicker(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public boolean reset(){
        String fName="[clear]";
        try {
            gPlayers=new ArrayList<>();
            gOldPlayers=new ArrayList<>();
            logger.info(fName+"reseted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean ifPlayer(Member member){
        String fName="[ifPlayer]";
        try {
            logger.info(fName+"member="+member.getId());
            if(gPlayers.isEmpty()){
                logger.info(fName+"lisy is Empty>false");
                return false;
            }
            for(entityPlayer player:gPlayers){
                logger.info(fName+"player.member="+player.getMemberID());
                if(player.getMemberID()==member.getIdLong()){
                    logger.info(fName+"member is aplayer>true");
                    return true;
                }
            }
            logger.info(fName+"member is not a player>false");
            return false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public int getIndex4Player(Member member){
        String fName="[getIndex4Player]";
        try {
            logger.info(fName+"member="+member.getId());
            if(gPlayers.isEmpty()){
                logger.info(fName+"lisy is Empty>false");
                return -1;
            }
            for(int i=0;i<gPlayers.size();i++){
                entityPlayer player=gPlayers.get(i);
                logger.info(fName+"player["+i+"].member="+player.getMemberID());
                if(player.getMemberID()==member.getIdLong()){
                    logger.info(fName+"member is a player>true");
                    return i;
                }
            }
            logger.info(fName+"member is not a player>false");
            return -1;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public boolean addPlayer(Member member){
        String fName="[addPlayer]";
        try {
            logger.info(fName+"member="+member.getId());
            if(ifPlayer(member)){
                logger.info(fName+"already added>false");
                return false;
            }
            logger.info(fName+"adding");
            entityPlayer player=new entityPlayer(member);
            gPlayers.add(player);
            logger.info(fName+"added>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean remPlayer(Member member){
        String fName="[remPlayer]";
        try {
            logger.info(fName+"member="+member.getId());
            int index=getIndex4Player(member);
            logger.info(fName+"index="+index);
            if(index<0){
                logger.info(fName+"index is bellow 0>false");
                return false;
            }
            logger.info(fName+"removing");
            gPlayers.remove(index);
            logger.info(fName+"removed>true");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
