package models.ls;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface lsChannelHelper
{
    static TextChannel lsGetTextChannelById(Guild guild, String id){
        String fName="[lsGetTextChannelById].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            TextChannel channel=guild.getTextChannelById(id);
            return channel;
        }
        catch(Exception e){
            logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(Guild guild, long id){
        String fName="[getTextChannelsById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  guild.getTextChannelById(id);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetFirstTextChannelByName(Guild guild, String channelName){
    String fName="[getFirstTextChannelByName1]";
    Logger logger = Logger.getLogger(lsChannelHelper.class);
    try{
        return  guild.getTextChannelsByName(channelName,true).get(0);
    }
    catch(Exception e){
         logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
        return null;
    }
}
    static List<TextChannel> lsGetTextChannelsByName(Guild guild, String channelName){
        String fName="[getTextChannelsByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  guild.getTextChannelsByName(channelName,true);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetFirstTextChannelByName(Guild guild, String channelName,boolean ignorecase){
        String fName="[getFirstTextChannelByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  guild.getTextChannelsByName(channelName,ignorecase).get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<TextChannel> lsGetTextChannelsByName(Guild guild, String channelName,boolean ignorecase){
        String fName="[getTextChannelsByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  guild.getTextChannelsByName(channelName,ignorecase);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static TextChannel lsGetTextChannelById(JDA jda, String id){
        String fName="[lsGetTextChannelById].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            TextChannel channel=jda.getTextChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(JDA jda, long id){
        String fName="[getTextChannelsById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  jda.getTextChannelById(id);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetFirstTextChannelByName(JDA jda, String channelName){
        String fName="[getFirstTextChannelByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  jda.getTextChannelsByName(channelName,true).get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<TextChannel> lsGetTextChannelsByName(JDA jda, String channelName){
        String fName="[getTextChannelsByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  jda.getTextChannelsByName(channelName,true);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetFirstTextChannelByName(JDA jda, String channelName,boolean ignorecase){
        String fName="[getFirstTextChannelByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  jda.getTextChannelsByName(channelName,ignorecase).get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<TextChannel> lsGetTextChannelsByName(JDA jda, String channelName,boolean ignorecase){
        String fName="[getTextChannelsByName1]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  jda.getTextChannelsByName(channelName,ignorecase);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static TextChannel lsGetTextChannelById(List<JDA> jdas, String id){
        String fName="[lsGetTextChannelById].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            TextChannel channel=null;
            for(JDA jda:jdas){
                channel=lsGetTextChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(List<JDA> jdas, long id){
        String fName="[getTextChannelsById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            TextChannel channel=null;
            for(JDA jda:jdas){
                channel=lsGetTextChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(ShardManager shardManager, String id){
        String fName="[lsGetTextChannelById].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            TextChannel channel=shardManager.getTextChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static TextChannel lsGetTextChannelById(ShardManager shardManager, long id){
        String fName="[getTextChannelsById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return shardManager.getTextChannelById(id);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static String lsGetTextChannelMention(Guild guild, String id){
        String fName="[lsGetTextChannelMention].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            TextChannel channel=guild.getTextChannelById(id);
            return channel.getAsMention();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "<#"+id+">";
        }
    }
    static String lsGetTextChannelMention(Guild guild, long id){
        String fName="[lsGetTextChannelMention].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            TextChannel channel=guild.getTextChannelById(id);
            return channel.getAsMention();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "<#"+id+">";
        }
    }
    static String lsGetTextChannelMention(TextChannel textChannel){
        String fName="[lsGetTextChannelMention].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return textChannel.getAsMention();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static List<String> lsGetTextChannelsMentionAsList(List<TextChannel>textChannels){
        String fName="lsGetTextChannelsMentionAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            List<String>result=new ArrayList<>();
            for(TextChannel textChannel:textChannels){
                try {
                    result.add(textChannel.getAsMention());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    static List<String> lsGetTextChannelsNameAsList(List<TextChannel>textChannels){
        String fName="lsGetTextChannelsNameAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            List<String>result=new ArrayList<>();
            for(TextChannel textChannel:textChannels){
                try {
                    result.add(textChannel.getName());
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    static String lsGetTextChannelsMentionAsString(List<TextChannel>textChannels,String divider){
        String fName="lsGetTextChannelsMentionAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider);
            StringBuilder result= new StringBuilder();
            for(TextChannel textChannel:textChannels){
                try {
                   if(result.length()!=0){
                       result.append(divider).append(textChannel.getAsMention());
                   }else{
                       result = new StringBuilder(textChannel.getAsMention());
                   }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String lsGetTextChannelsNameAsString(List<TextChannel>textChannels,String divider){
        String fName="lsGetTextChannelsNameAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider);
            StringBuilder result= new StringBuilder();
            for(TextChannel textChannel:textChannels){
                try {
                    if(result.length()!=0){
                        result.append(divider).append(textChannel.getAsMention());
                    }else{
                        result = new StringBuilder(textChannel.getAsMention());
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static List<String> lsGetTextChannelsMentionAsList(List<Long>ids,Guild guild){
        String fName="lsGetTextChannelsMentionAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"guild.id="+guild.getId());
            List<String>result=new ArrayList<>();
            for(Long id:ids){
                try {
                    TextChannel textChannel=guild.getTextChannelById(id);
                    if(textChannel!=null){
                        result.add(textChannel.getAsMention());
                    }else{
                        result.add("<"+id+">");
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    result.add("<"+id+">");
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    static List<String> lsGetTextChannelsNameAsList(List<Long>ids,Guild guild){
        String fName="lsGetTextChannelsNameAsList";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"guild.id="+guild.getId());
            List<String>result=new ArrayList<>();
            for(Long id:ids){
                try {
                    TextChannel textChannel=guild.getTextChannelById(id);
                    if(textChannel!=null){
                        result.add(textChannel.getName());
                    }else{
                        result.add("<"+id+">");
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    result.add("<"+id+">");
                }
            }
            logger.info(fName+"result="+result.toString());
            return  result;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return new ArrayList<>();
        }
    }
    static String lsGetTextChannelsMentionAsString(List<Long>ids,String divider,Guild guild){
        String fName="lsGetTextChannelsMentionAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider+", guild.id="+guild.getId());
            StringBuilder result= new StringBuilder();
            for(Long id:ids){
                try {
                    TextChannel textChannel=guild.getTextChannelById(id);
                    if(textChannel!=null){
                        if(result.length()!=0){
                            result.append(divider).append(textChannel.getAsMention());
                        }else{
                            result = new StringBuilder(textChannel.getAsMention());
                        }
                    }else{
                        if(result.length()!=0){
                            result.append(divider).append("<"+id+">");
                        }else{
                            result = new StringBuilder("<"+id+">");
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    if(result.length()!=0){
                        result.append(divider).append("<"+id+">");
                    }else{
                        result = new StringBuilder("<"+id+">");
                    }
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    static String lsGetTextChannelsNameAsString(List<Long>ids,String divider,Guild guild){
        String fName="lsGetTextChannelsNameAsString";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName+"divider="+divider+", guild.id="+guild.getId());
            StringBuilder result= new StringBuilder();
            for(Long id:ids){
                try {
                    TextChannel textChannel=guild.getTextChannelById(id);
                    if(textChannel!=null){
                        if(result.length()!=0){
                            result.append(divider).append(textChannel.getName());
                        }else{
                            result = new StringBuilder(textChannel.getName());
                        }
                    }else{
                        if(result.length()!=0){
                            result.append(divider).append("<"+id+">");
                        }else{
                            result = new StringBuilder("<"+id+">");
                        }
                    }
                }catch (Exception e){
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    if(result.length()!=0){
                        result.append(divider).append("<"+id+">");
                    }else{
                        result = new StringBuilder("<"+id+">");
                    }
                }
            }
            logger.info(fName+"result="+result);
            return result.toString();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    static VoiceChannel lsGetVoiceChannelById(Guild guild, String id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            VoiceChannel channel=guild.getVoiceChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetVoiceChannelById(Guild guild, long id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            VoiceChannel channel=guild.getVoiceChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetFirstVoiceChannelByName(Guild guild, String name){
        String fName="lsGetFirstVoiceChannelByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            List<VoiceChannel> channels=guild.getVoiceChannelsByName(name,true);
            if(channels.isEmpty()){return  null;}
            return channels.get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<VoiceChannel> lsGetVoiceChannelsByName(Guild guild, String name){
        String fName="lsGetFirstVoiceChannelsByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return  guild.getVoiceChannelsByName(name,true);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetFirstVoiceChannelByName(Guild guild, String name,boolean ignorecase){
        String fName="lsGetFirstVoiceChannelByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            List<VoiceChannel> channels=guild.getVoiceChannelsByName(name,ignorecase);
            if(channels.isEmpty()){return  null;}
            return channels.get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<VoiceChannel> lsGetVoiceChannelsByName(Guild guild, String name,boolean ignorecase){
        String fName="lsGetFirstVoiceChannelsByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return  guild.getVoiceChannelsByName(name,ignorecase);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static VoiceChannel lsGetVoiceChannelById(JDA jda, String id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            VoiceChannel channel=jda.getVoiceChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetVoiceChannelById(JDA jda, long id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            VoiceChannel channel=jda.getVoiceChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetFirstVoiceChannelByName(JDA jda, String name){
        String fName="lsGetFirstVoiceChannelByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            List<VoiceChannel> channels=jda.getVoiceChannelsByName(name,true);
            if(channels.isEmpty()){return  null;}
            return channels.get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<VoiceChannel> lsGetVoiceChannelsByName(JDA jda, String name){
        String fName="lsGetFirstVoiceChannelsByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return  jda.getVoiceChannelsByName(name,true);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetFirstVoiceChannelByName(JDA jda, String name,boolean ignorecase){
        String fName="lsGetFirstVoiceChannelByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            List<VoiceChannel> channels=jda.getVoiceChannelsByName(name,ignorecase);
            if(channels.isEmpty()){return  null;}
            return channels.get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<VoiceChannel> lsGetVoiceChannelsByName(JDA jda, String name,boolean ignorecase){
        String fName="lsGetFirstVoiceChannelsByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return  jda.getVoiceChannelsByName(name,ignorecase);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static VoiceChannel lsGetVoiceChannelById(ShardManager shardManager, String id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            VoiceChannel channel=shardManager.getVoiceChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetVoiceChannelById(ShardManager shardManager, long id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            VoiceChannel channel=shardManager.getVoiceChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetVoiceChannelById(List<JDA> jdas, String id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            VoiceChannel channel=null;
            for(JDA jda:jdas){
                channel=lsGetVoiceChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static VoiceChannel lsGetVoiceChannelById(List<JDA> jdas, long id){
        String fName="lsGetVoiceChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            VoiceChannel channel=null;
            for(JDA jda:jdas){
                channel=lsGetVoiceChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static Category lsGetCategoryById(Guild guild, String id){
        String fName="lsGetCategoryById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            Category channel=guild.getCategoryById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Category lsGetCategoryById(Guild guild, long id){
        String fName="lsGetCategoryById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            Category channel=guild.getCategoryById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Category lsGetFirstCategoryChannelByName(Guild guild, String name){
        String fName="lsGetFirstCategoryChannelByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            List<Category> channels=guild.getCategoriesByName(name,true);
            if(channels.isEmpty()){return  null;}
            return channels.get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<Category> lsGetCategoryChannelsByName(Guild guild, String name){
        String fName="lsGetFirsttCategoryChannelsByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return  guild.getCategoriesByName(name,true);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Category lsGetFirstCategoryChannelByName(Guild guild, String name,boolean ignorecase){
        String fName="lsGetFirstCategoryChannelByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            List<Category> channels=guild.getCategoriesByName(name,ignorecase);
            if(channels.isEmpty()){return  null;}
            return channels.get(0);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static List<Category> lsGetCategoryChannelsByName(Guild guild, String name,boolean ignorecase){
        String fName="lsGetFirstCategoryChannelsByName.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            return  guild.getCategoriesByName(name,ignorecase);
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static GuildChannel lsGetGuildChannelById(Guild guild, String id){
        String fName="lsGetGuildChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            GuildChannel channel=guild.getGuildChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static boolean lsIsGuildChannel(GuildChannel guildChannel){
        String fName="lsGetGuildChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            if(guildChannel.getType()==ChannelType.TEXT||guildChannel.getType()==ChannelType.VOICE||guildChannel.getType()==ChannelType.CATEGORY||guildChannel.getType()==ChannelType.STORE){
                return true;
            }
            return false;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static String lsGetChannelMentionById(Guild guild, String id){
        String fName="lsGetGuildChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            GuildChannel channel=guild.getGuildChannelById(id);
            if(channel==null){
                return "<"+id+">";
            }
            if(channel.getType()!=ChannelType.TEXT){
                return channel.getName();
            }
            TextChannel textChannel=guild.getTextChannelById(id);
            return textChannel.getAsMention();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }


    static StoreChannel lsGetStoreChannelById(Guild guild, String id){
        String fName="lsGetCategoryById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            StoreChannel channel=guild.getStoreChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static StoreChannel lsGetStoreChannelById(Guild guild, long id){
        String fName="lsGetCategoryById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            StoreChannel channel=guild.getStoreChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    static boolean lsHasTextChannelById(Guild guild, long id){
        String fName="hasTextChannelsById";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  guild.getTextChannelById(id)!=null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    static boolean lsIsNSFWTextChannelById(Guild guild, long id){
        String fName="IsNSFWTextChannelsById";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return  guild.getTextChannelById(id).isNSFW();
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


    static PrivateChannel lsGetPrivateChannelById(ShardManager shardManager, String id){
        String fName="lsGetPrivateChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            PrivateChannel channel=shardManager.getPrivateChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(ShardManager shardManager, long id){
        String fName="lsGetPrivateChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            PrivateChannel channel=shardManager.getPrivateChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(JDA jda, String id){
        String fName="lsGetPrivateChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            PrivateChannel channel=jda.getPrivateChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(JDA jda, long id){
        String fName="lsGetPrivateChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            PrivateChannel channel=jda.getPrivateChannelById(id);
            return channel;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(List<JDA> jdas, String id){
        String fName="[lsGetPrivateChannelById].";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            PrivateChannel channel=null;
            for(JDA jda:jdas){
                channel=lsGetPrivateChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(List<JDA> jdas, long id){
        String fName="[lsGetPrivateChannelById]";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            PrivateChannel channel=null;
            for(JDA jda:jdas){
                channel=lsGetPrivateChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(lcGlobalHelper global, String id){
        String fName="lsGetPrivateChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            id=id.replaceAll("<#","").replaceAll(">","");
            PrivateChannel channel=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                channel=lsGetPrivateChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static PrivateChannel lsGetPrivateChannelById(lcGlobalHelper global, long id){
        String fName="lsGetPrivateChannelById.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            logger.info(fName);
            PrivateChannel channel=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                channel=lsGetPrivateChannelById(jda, id);
                if(channel!=null){
                    return channel;
                }
            }
            return null;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    interface PermissionOverrides{
        //for JDA update to 5
        /*static List<PermissionOverride> lsGetPermissionOverrides(TextChannel channel){
            String fName="lsGetPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getPermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetPermissionOverrides(VoiceChannel channel){
            String fName="lsGetPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getPermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetPermissionOverrides(Category channel){
            String fName="lsGetPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getPermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetRolePermissionOverrides(TextChannel channel){
            String fName="lsGetRolePermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetRolePermissionOverrides(VoiceChannel channel){
            String fName="lsGetRolePermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetRolePermissionOverrides(Category channel){
            String fName="lsGetRolePermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static PermissionOverride lsGetRolePermissionOverride(TextChannel channel,Role role){
            String fName="lsGetRolePermissionOverride.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(role==null)throw  new Exception("No Role provided!");
                if(role.getGuild().getIdLong()!=channel.getGuild().getIdLong())throw  new Exception("Not same guild!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(PermissionOverride permissionOverride:list){
                    if(permissionOverride.getRole().getIdLong()==role.getIdLong()){
                        logger.info(fName+"found");
                        return permissionOverride;
                    }
                }
                logger.info(fName+"not found");
                return  null;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static PermissionOverride lsGetRolePermissionOverride(VoiceChannel channel,Role role){
            String fName="lsGetRolePermissionOverride.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(role==null)throw  new Exception("No Role provided!");
                if(role.getGuild().getIdLong()!=channel.getGuild().getIdLong())throw  new Exception("Not same guild!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(PermissionOverride permissionOverride:list){
                    if(permissionOverride.getRole().getIdLong()==role.getIdLong()){
                        logger.info(fName+"found");
                        return permissionOverride;
                    }
                }
                logger.info(fName+"not found");
                return  null;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static PermissionOverride lsGetRolePermissionOverride(Category channel,Role role){
            String fName="lsGetRolePermissionOverride.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(role==null)throw  new Exception("No Role provided!");
                if(role.getGuild().getIdLong()!=channel.getGuild().getIdLong())throw  new Exception("Not same guild!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(PermissionOverride permissionOverride:list){
                    if(permissionOverride.getRole().getIdLong()==role.getIdLong()){
                        logger.info(fName+"found");
                        return permissionOverride;
                    }
                }
                logger.info(fName+"not found");
                return  null;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetRolePermissionOverrides(TextChannel channel,List<Role> roles){
            String fName="lsGetRolePermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(roles==null)throw  new Exception("No Role provided!");
                if(roles.isEmpty())throw  new Exception("No Role provided!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                List<PermissionOverride>result=new ArrayList<>();
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(Role role:roles){
                    if(role.getGuild().getIdLong()!=channel.getGuild().getIdLong()){
                        logger.warn(fName+"Not same guild!");
                    }else for(PermissionOverride permissionOverride:list){
                        if(permissionOverride.getRole().getIdLong()==role.getIdLong()){
                            logger.info(fName+"add");
                            result.add(permissionOverride);
                        }
                    }
                }
                logger.info(fName+"result.size="+result.size());
                return  result;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetRolePermissionOverrides(VoiceChannel channel,List<Role> roles){
            String fName="lsGetRolePermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(roles==null)throw  new Exception("No Role provided!");
                if(roles.isEmpty())throw  new Exception("No Role provided!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                List<PermissionOverride>result=new ArrayList<>();
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(Role role:roles){
                    if(role.getGuild().getIdLong()!=channel.getGuild().getIdLong()){
                        logger.warn(fName+"Not same guild!");
                    }else for(PermissionOverride permissionOverride:list){
                        if(permissionOverride.getRole().getIdLong()==role.getIdLong()){
                            logger.info(fName+"add");
                            result.add(permissionOverride);
                        }
                    }
                }
                logger.info(fName+"result.size="+result.size());
                return  result;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetRolePermissionOverrides(Category channel,List<Role> roles){
            String fName="lsGetRolePermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(roles==null)throw  new Exception("No Role provided!");
                if(roles.isEmpty())throw  new Exception("No Role provided!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getRolePermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                List<PermissionOverride>result=new ArrayList<>();
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(Role role:roles){
                    if(role.getGuild().getIdLong()!=channel.getGuild().getIdLong()){
                        logger.warn(fName+"Not same guild!");
                    }else for(PermissionOverride permissionOverride:list){
                        if(permissionOverride.getRole().getIdLong()==role.getIdLong()){
                            logger.info(fName+"add");
                            result.add(permissionOverride);
                        }
                    }
                }
                logger.info(fName+"result.size="+result.size());
                return  result;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetMemberPermissionOverrides(TextChannel channel){
            String fName="lsGetMemberPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                return list;
            }
            catch(Exception e){
                 logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetMemberPermissionOverrides(VoiceChannel channel){
            String fName="lsGetMemberPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                return list;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetMemberPermissionOverrides(Category channel){
            String fName="lsGetMemberPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                //Category category=channel.getPermissionOverride();
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                return list;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static PermissionOverride lsGetMemberPermissionOverride(TextChannel channel,Member member){
            String fName="lsGetMemberPermissionOverride.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(member==null)throw  new Exception("No member provided!");
                if(member.getGuild().getIdLong()!=channel.getGuild().getIdLong())throw  new Exception("Not same guild!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(PermissionOverride permissionOverride:list){
                    if(permissionOverride.getMember().getIdLong()==member.getIdLong()){
                        logger.info(fName+"found");
                        return permissionOverride;
                    }
                }
                logger.info(fName+"not found");
                return  null;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static PermissionOverride lsGetMemberPermissionOverride(VoiceChannel channel,Member member){
            String fName="lsGetMemberPermissionOverride.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(member==null)throw  new Exception("No member provided!");
                if(member.getGuild().getIdLong()!=channel.getGuild().getIdLong())throw  new Exception("Not same guild!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(PermissionOverride permissionOverride:list){
                    if(permissionOverride.getMember().getIdLong()==member.getIdLong()){
                        logger.info(fName+"found");
                        return permissionOverride;
                    }
                }
                logger.info(fName+"not found");
                return  null;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static PermissionOverride lsGetMemberPermissionOverride(Category channel,Member member){
            String fName="lsGetMemberPermissionOverride.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(member==null)throw  new Exception("No member provided!");
                if(member.getGuild().getIdLong()!=channel.getGuild().getIdLong())throw  new Exception("Not same guild!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(PermissionOverride permissionOverride:list){
                    if(permissionOverride.getMember().getIdLong()==member.getIdLong()){
                        logger.info(fName+"found");
                        return permissionOverride;
                    }
                }
                logger.info(fName+"not found");
                return  null;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetMemberPermissionOverrides(TextChannel channel,List<Member> members){
            String fName="lsGetMemberPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(members==null)throw  new Exception("No Members provided!");
                if(members.isEmpty())throw  new Exception("No Members provided!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                List<PermissionOverride>result=new ArrayList<>();
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(Member member:members){
                    if(member.getGuild().getIdLong()!=channel.getGuild().getIdLong()){
                        logger.warn(fName+"Not same guild!");
                    }else for(PermissionOverride permissionOverride:list){
                        if(permissionOverride.getMember().getIdLong()==member.getIdLong()){
                            logger.info(fName+"add");
                            result.add(permissionOverride);
                        }
                    }
                }
                logger.info(fName+"result.size="+result.size());
                return  result;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetMemberPermissionOverrides(VoiceChannel channel,List<Member> members){
            String fName="lsGetMemberPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(members==null)throw  new Exception("No Members provided!");
                if(members.isEmpty())throw  new Exception("No Members provided!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                List<PermissionOverride>result=new ArrayList<>();
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(Member member:members){
                    if(member.getGuild().getIdLong()!=channel.getGuild().getIdLong()){
                        logger.warn(fName+"Not same guild!");
                    }else for(PermissionOverride permissionOverride:list){
                        if(permissionOverride.getMember().getIdLong()==member.getIdLong()){
                            logger.info(fName+"add");
                            result.add(permissionOverride);
                        }
                    }
                }
                logger.info(fName+"result.size="+result.size());
                return  result;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        static List<PermissionOverride> lsGetMemberPermissionOverrides(Category channel,List<Member> members){
            String fName="lsGetMemberPermissionOverrides.";Logger logger = Logger.getLogger(lsChannelHelper.class);
            try{
                //for JDA update to 5
                logger.info(fName);
                if(members==null)throw  new Exception("No Members provided!");
                if(members.isEmpty())throw  new Exception("No Members provided!");
                //Category category=channel.getPermissionOverride(role);
                List<PermissionOverride>list=  channel.getMemberPermissionOverrides();
                if(list==null)throw  new Exception("list is null, has no permission overrides!");
                List<PermissionOverride>result=new ArrayList<>();
                if(list.isEmpty()){
                    logger.info(fName+"list is empty, has no permission overrides!");
                    return  null;
                }
                for(Member member:members){
                    if(member.getGuild().getIdLong()!=channel.getGuild().getIdLong()){
                        logger.warn(fName+"Not same guild!");
                    }else for(PermissionOverride permissionOverride:list){
                        if(permissionOverride.getMember().getIdLong()==member.getIdLong()){
                            logger.info(fName+"add");
                            result.add(permissionOverride);
                        }
                    }
                }
                logger.info(fName+"result.size="+result.size());
                return  result;
            }
            catch(Exception e){
                logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        */
    }

    //for JDA update to 5
    /*static Category lsGetChannelParent(TextChannel channel){
        String fName="lsGetChannelParent.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            //for JDA update to 5
            logger.info(fName);
            //Category category=channel.getParent();
            Category category=channel.getParentCategory();
            return category;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Category lsGetChannelParent(VoiceChannel channel){
        String fName="lsGetChannelParent.";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            //for JDA update to 5
            logger.info(fName);
            //Category category=channel.getParent();
            Category category=channel.getParentCategory();
            return category;
        }
        catch(Exception e){
             logger.error(".exception=" + e+"\nTrace:"+Arrays.toString(e.getStackTrace()));
            return null;
        }
    }*/
}