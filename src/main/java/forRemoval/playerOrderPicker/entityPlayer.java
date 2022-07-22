package forRemoval.playerOrderPicker;

import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.entities.Member;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.Arrays;

public class entityPlayer {
    Logger logger = Logger.getLogger(getClass()); String cName="[entityPlayer]";
    Member gMember=null;
    int gSkip=0;
    int gSelected=0;
    long gTimestampJoin=0;
    long gTimestampLastSelected=0;
    long gTimestampLastSkipped=0;
    public entityPlayer(){
        String fName="[constructor]";
        logger.info(fName);
    }
    public entityPlayer(Member member){
        String fName="[constructor]";
        try {
            gMember=member;
            logger.info(fName+"member="+gMember.getId());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public boolean reset(){
        String fName="[clear]";
        try {
            gMember=null;
            gSkip=0;
            gSelected=0;
            gTimestampJoin=0;
            gTimestampLastSelected=0;
            gTimestampLastSkipped=0;
            logger.info(fName+"reseted");
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Member getMember(){
        String fName="[getMember]";
        try {
            logger.info(fName+"value="+gMember.getId());
            return gMember;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getMemberID(){
        String fName="[getMemberID]";
        try {
            logger.info(fName+"value="+gMember.getId());
            return gMember.getIdLong();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int getSkip(){
        String fName="[getSkip]";
        try {
            logger.info(fName+"value="+gSkip);
            return gSkip;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public int getSelected(){
        String fName="[getSelected]";
        try {
            logger.info(fName+"value="+gSelected);
            return gSelected;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public long getTimestampJoin(){
        String fName="[getTimestampJoin]";
        try {
            logger.info(fName+"value="+gTimestampJoin);
            return gTimestampJoin;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public long getTimestampLastSelected(){
        String fName="[getTimestampLastSelected]";
        try {
            logger.info(fName+"value="+gTimestampLastSelected);
            return gTimestampLastSelected;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public long getTimestampLastSkipped(){
        String fName="[getTimestampLastSkipped]";
        try {
            logger.info(fName+"value="+gTimestampLastSkipped);
            return gTimestampLastSkipped;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return -1;
        }
    }
    public boolean setSkip(int input){
        String fName="[setSkip]";
        try {
            gSkip=input;
            logger.info(fName+"value="+gSkip);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setSelected(int input){
        String fName="[setSelected]";
        try {
            gSelected=input;
            logger.info(fName+"value="+gSelected);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTimestampJoin(long input){
        String fName="[setTimestampJoin]";
        try {
            gTimestampJoin=input;
            logger.info(fName+"value="+gTimestampJoin);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTimestampLastSelected(long input){
        String fName="[setTimestampLastSelected]";
        try {
            gTimestampLastSelected=input;
            logger.info(fName+"value="+gTimestampLastSelected);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTimestampLastSkipped(long input){
        String fName="[setTimestampLastSkipped]";
        try {
            gTimestampLastSkipped=input;
            logger.info(fName+"value="+gTimestampLastSkipped);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean incSkip(){
        String fName="[incSkip]";
        try {
            gSkip++;
            logger.info(fName+"value="+gSkip);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean incSelected(){
        String fName="[incSelected]";
        try {
            gSelected++;
            logger.info(fName+"value="+gSelected);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean decSkip(){
        String fName="[decSkip]";
        try {
            gSkip--;
            if(gSkip<0)gSkip=0;
            logger.info(fName+"value="+gSkip);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean decSelected(){
        String fName="[decSelected]";
        try {
            gSelected--;
            if(gSelected<0)gSelected=0;
            logger.info(fName+"value="+gSelected);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    private long getCurrentTimeMillis(){
        String fName="[getCurrentTimeMillis]";
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logger.info(fName+".timestamp="+timestamp.getTime());
            return timestamp.getTime();
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0L;
        }
    }
    public boolean setTimestampJoin(){
        String fName="[setTimestampJoin]";
        try {
            gTimestampJoin=getCurrentTimeMillis();
            logger.info(fName+"value="+gTimestampJoin);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTimestampLastSelected(){
        String fName="[setTimestampLastSelected]";
        try {
            gTimestampLastSelected=getCurrentTimeMillis();
            logger.info(fName+"value="+gTimestampLastSelected);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean setTimestampLastSkipped(){
        String fName="[setTimestampLastSkipped]";
        try {
            gTimestampLastSkipped=getCurrentTimeMillis();
            logger.info(fName+"value="+gTimestampLastSkipped);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
