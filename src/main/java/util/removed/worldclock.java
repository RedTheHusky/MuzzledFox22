package util.removed;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import models.lcGlobalHelper;
import models.ll.llMemberHelper;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.awt.*;
import java.text.Normalizer;
import java.time.ZoneId;
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class worldclock extends Command implements llMessageHelper, llGlobalHelper, llMemberHelper {
    String cName="[worldclock]";
    EventWaiter gWaiter;
    Logger logger = Logger.getLogger(getClass());
    lcGlobalHelper global;
    CommandEvent gEvent; User gUser; Guild gGuild; TextChannel gTextChannel;
    String sTitle="World Clock";
    String channelSymbol="⌚";
    public worldclock(lcGlobalHelper g){
        String fName=".constructor";
        logger.info(cName+fName);
        this.name = "World Clock";
        this.help = "worldclock";
         global=g; gWaiter=g.waiter;
        this.aliases = new String[]{"wclock"};
        this.guildOnly = true;this.hidden=true;
        task= new TimerTask() {
            @Override
            public void run() {
                timerRefresh();
            };
        };
    }
    protected void execute(CommandEvent event) {
        String fName="[execute]";
        logger.info(cName+fName);
        gEvent=event;
        gUser=event.getAuthor();gGuild=event.getGuild();gTextChannel=event.getTextChannel();
        logger.info(cName + fName + ".user:" + gUser.getId() + "|" + gUser.getName());
        logger.info(cName + fName + ".guild:" + gGuild.getId() + "|" + gGuild.getName());
        logger.info(cName + fName + ".TextChannel:" + gTextChannel.getId() + "|" + gTextChannel.getName());
        if(llDebug){
            logger.info(cName+fName+".global debug true");return;
        }
        Boolean isInvalidCommand = true;
        if(event.getArgs().isEmpty()){
            logger.info(cName+fName+".Args=0");
            help("main");isInvalidCommand=false;
        }else {
            logger.info(cName + fName + ".Args");
            String []items = event.getArgs().split("\\s+");
            if(items[0].equalsIgnoreCase("test")){
                testClock1();isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("refresh")){
                refresh();isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("refreshall")){
                refreshAll();isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("start")){
                start();isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("stop")){
                stop();isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("help")){
                help("main");isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("city")){
                askTime4CityName();isInvalidCommand = false;
            }
            if(items[0].equalsIgnoreCase("id")){
               askTime4ID();isInvalidCommand = false;
            }
        }
        logger.info(cName+fName+".deleting op message");
        llMessageDelete(event);
        if(isInvalidCommand){
            llSendQuickEmbedMessage(event.getAuthor(),sTitle,"Provided an incorrect command!", llColorRed);
        }
    }
    private void help( String command){
        String fName="[help]";
        logger.info(cName + fName + ".command:"+command);
        EmbedBuilder embed= new EmbedBuilder();
        embed.setColor(llColorBlue1);
        embed.setTitle("World Clock Utility!");
        String quickSummonWithSpace=llPrefixStr+"wclock ";
        String desc="n/a";
        if(command.equalsIgnoreCase("main")){
            desc="\n`"+quickSummonWithSpace+"city [city]` get the city time";
            desc+="\n`"+quickSummonWithSpace+"id [id]` get the city time";
            desc+="\n`"+quickSummonWithSpace+"refresh` refresh current guild";
            if(llMemberIsManager(gEvent.getMember())){
                desc+="\n`"+quickSummonWithSpace+"start` starts tracker";
                desc+="\n`"+quickSummonWithSpace+"stop` stops tracker";
                desc+="\n`"+quickSummonWithSpace+"refreshall` refresh all guilds";
            }
        }
        embed.setDescription(desc);
        llSendMessage(gUser,embed);
    }
    private void testClock1(){
        String fName="[testClock1]";
        logger.info(cName + fName);
        getDate();
    }
    private void refresh(){
        String fName="[refresh]";
        logger.info(cName + fName);
        getDate();
        updateVoiceChannelByName(gGuild);
    }
    private void refreshAll(){
        String fName="[refreshAll]";
        logger.info(cName + fName);
        getDate();
        Map<String, Guild> guilds=global.getGuildMap4JDA();
        for(Map.Entry<String, Guild> entry : guilds.entrySet()) {
            String key = entry.getKey();
            Guild guild = entry.getValue();
            updateVoiceChannelByName(guild);
        }
    }
    Calendar gToday; //List<String> gTimezones;
    Set<String> zids = ZoneId.getAvailableZoneIds();
    private void getDate(){
        String fName="[getDate]";
        logger.info(cName + fName);
        gToday = Calendar.getInstance();zids = ZoneId.getAvailableZoneIds();
    }
    private void start() {
        String fName = "[start]";
        logger.info(cName + fName);
        long delay = 0;
        long intevalPeriod = 30;
        global.worldclock.timerTrackerStart(task, delay,intevalPeriod);
        llSendQuickEmbedMessage(gUser,sTitle,"Start timer", llColorGreen1);
    }
    private void stop() {
        String fName = "[stop]";
        logger.info(cName + fName);
        global.worldclock.timerTrackerStop();
        llSendQuickEmbedMessage(gUser,sTitle,"Stop timer", llColorRed);
    }
    protected ScheduledExecutorService timerTracker;
    public TimerTask task;
    public worldclock(Boolean thread){
        if(thread)timerTracker = Executors.newScheduledThreadPool(1);
    }
    ScheduledFuture<?> future;
    protected void timerTrackerCreate(TimerTask task, long delay, long interval){
        String fName="timerTrackerCreate";
        future = timerTracker.scheduleAtFixedRate(
                () -> {logger.info(cName+fName+".run");task.run();},
                delay,
                interval,
                TimeUnit.SECONDS);
    }
    public Boolean timerTrackerStart(TimerTask task, long delay, long interval){
        String fName="timerTrackerStart";
        if(future==null){
            logger.info(cName+fName+".null>create");
            timerTrackerCreate(task, delay, interval);
        }
        if(future.isCancelled()||future.isDone()){
            logger.info(cName+fName+".done or canceled >create");
            timerTrackerCreate(task, delay, interval);
        }else{
            future.cancel(true);
            timerTrackerCreate(task, delay, interval);
        }
        logger.info(cName+fName+".started");
        return true;
    }
    public Boolean timerTrackerStop(){
        String fName="timerBGRegionTrackerStop";
        if(future==null){
            logger.info(cName+fName+".null>false"); return false;
        }
        if(future.isCancelled()||future.isDone()) {
            logger.info(cName+fName+".done or canceled >false"); return false;
        }
        future.cancel(false);
        logger.info(cName+fName+".stopped");
        return true;
    }
    private void timerRefresh(){
        String fName="[timerRefresh]";
        logger.info(cName + fName);
        try{
            getDate();
            Map<String, Guild> guilds=global.getGuildMap4JDA();
            for(Map.Entry<String, Guild> entry : guilds.entrySet()) {
                String key = entry.getKey();
                Guild guild = entry.getValue();
                updateVoiceChannelByName(guild);
            }
        }catch (Exception e){
            logger.error(cName+fName+".exception="+e);
        }
    }

    private void updateVoiceChannelByName(Guild guild){
        String fName="[updateVoiceChannelByName]";
        logger.info(cName + fName);
        List<VoiceChannel>channels=guild.getVoiceChannels();
        for(VoiceChannel channel : channels){
            try{
                String parentname=channel.getParent().getName().toLowerCase();
                logger.info(cName + fName + ". parentname="+ parentname);
                logger.info(cName + fName + ". channelName="+ channel.getName());
                if(parentname.contains("clock")) {
                    String t[]=channel.getName().split(":");
                    String cityName=t[0];
                    logger.info(cName + fName + ". cityName="+ cityName);
                    String tzCityName = Normalizer.normalize(cityName, Normalizer.Form.NFKD)
                            .trim().replaceAll("[^\\p{ASCII}-_ ]", "")
                            .replace(' ', '_');
                    logger.info(cName + fName + ". tzCityName="+tzCityName);
                    List<String> possibleTimeZones = zids.stream()
                            .filter(zid -> zid.endsWith("/" + tzCityName))
                            .collect(Collectors.toList());
                    if(possibleTimeZones.isEmpty()){
                        logger.info(cName + fName + ". Stream<String> null");

                    }else{
                        String possibleTimeZone=possibleTimeZones.get(0);
                        logger.info(cName + fName + ".  possibleTimeZone="+ possibleTimeZone);
                        TimeZone selectedTimeZone= TimeZone.getTimeZone(possibleTimeZone);
                        logger.info(cName + fName + ". selectedTimeZone="+ selectedTimeZone.getDisplayName());
                        gToday.setTimeZone(selectedTimeZone);
                        int hour=gToday.get(gToday.HOUR_OF_DAY);
                        int minute=gToday.get(gToday.MINUTE);
                        String value="";//gToday.get(gToday.HOUR_OF_DAY)+":"+gToday.get(gToday.MINUTE);
                        if(hour<10){
                            value="0"+gToday.get(gToday.HOUR_OF_DAY);
                        }else{
                            value= String.valueOf(gToday.get(gToday.HOUR_OF_DAY));
                        }
                        if(minute<10){
                            value+=":0"+gToday.get(gToday.MINUTE);
                        }else{
                            value+=":"+gToday.get(gToday.MINUTE);
                        }
                        logger.info(cName + fName + ". value="+ value);
                        String oldName=channel.getName();
                        String newName=cityName+": "+value;
                        if(newName.equalsIgnoreCase(oldName)){
                            logger.info(cName + fName + ". old and new name identical> ignore");
                        }else{
                            logger.info(cName + fName + ". old and new name different> update");
                            channel.getManager().setName(newName).queue();
                        }
                    }
                }
            }catch (Exception e){
                logger.error(cName+fName+".exception="+e);
            }

        }

    }
    private void askTime4CityName(){
        String fName="[askTime4CityName]";
        logger.info(cName + fName);
        getDate();
        String []items = gEvent.getArgs().split("\\s+");
        String text="";
        for(int i=1;i<items.length;i++){
            text+=" "+items[i];
        }
        logger.info(cName + fName+"text="+text);
        String tzCityName = Normalizer.normalize(text, Normalizer.Form.NFKD)
                .trim().replaceAll("[^\\p{ASCII}-_ ]", "")
                .replace(' ', '_');
        List<String> possibleTimeZones = zids.stream()
                .filter(zid -> zid.endsWith("/" + tzCityName))
                .collect(Collectors.toList());
        String value="";
        Color color= llColorRed;
        if(possibleTimeZones.isEmpty()){
            logger.info(cName + fName + ". Stream<String> null");
            value="Invalid entry:"+ text;
        }else{
            String possibleTimeZone=possibleTimeZones.get(0);
            logger.info(cName + fName + ".  possibleTimeZone="+ possibleTimeZone);
            TimeZone selectedTimeZone= TimeZone.getTimeZone(possibleTimeZone);
            logger.info(cName + fName + ". selectedTimeZone="+ selectedTimeZone.getDisplayName());
            gToday.setTimeZone(selectedTimeZone);
            color= llColorBlue1;
            value="**"+text+"** "+gToday.get(gToday.MONTH)+"."+gToday.get(gToday.DAY_OF_MONTH)+" ";
            int hour=gToday.get(gToday.HOUR_OF_DAY);
            int minute=gToday.get(gToday.MINUTE);
            if(hour<10){
                value+="0"+gToday.get(gToday.HOUR_OF_DAY);
            }else{
                value+= String.valueOf(gToday.get(gToday.HOUR_OF_DAY));
            }
            if(minute<10){
                value+=":0"+gToday.get(gToday.MINUTE);
            }else{
                value+=":"+gToday.get(gToday.MINUTE);
            }
        }
        logger.info(cName + fName + ". value="+ value);
        llSendQuickEmbedMessage(gTextChannel,sTitle,value,color);
    }
    private void askTime4ID(){
        String fName="[askTime4ID]";
        logger.info(cName + fName);
        getDate();
        String []items = gEvent.getArgs().split("\\s+");
        String text="";
        if(items.length>=2){
            text=items[1];
        }
        logger.info(cName + fName+"text="+text);
        String value="";
        Color color= llColorPurple1;
        TimeZone selectedTimeZone= TimeZone.getTimeZone(text);
        logger.info(cName + fName + ". selectedTimeZone="+ selectedTimeZone.getDisplayName());
        gToday.setTimeZone(selectedTimeZone);
        value="**"+text+"** "+gToday.get(gToday.MONTH)+"."+gToday.get(gToday.DAY_OF_MONTH)+" ";
        int hour=gToday.get(gToday.HOUR_OF_DAY);
        int minute=gToday.get(gToday.MINUTE);
        if(hour<10){
            value+="0"+gToday.get(gToday.HOUR_OF_DAY);
        }else{
            value+= String.valueOf(gToday.get(gToday.HOUR_OF_DAY));
        }
        if(minute<10){
            value+=":0"+gToday.get(gToday.MINUTE);
        }else{
            value+=":"+gToday.get(gToday.MINUTE);
        }
        logger.info(cName + fName + ". value="+ value);
        llSendQuickEmbedMessage(gTextChannel,sTitle,value,color);
    }
}

