package models.lc.interaction.applicationcommand;

import dev.cerus.jdasc.interaction.Interaction;
import dev.cerus.jdasc.interaction.InteractionType;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import models.lc.discordentities.lcMyEmbedBuilder;
import models.lc.discordentities.lcMyMessageAllowedMentionsBuilder;
import models.lc.discordentities.lcMyMessageJsonBuilder;
import models.lc.discordentities.lcMyMessageReferenceBuilder;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lc.interaction.messagecomponent.lcMessageBuildComponents;
import models.lcGlobalHelper;
import models.ll.llCommonKeys;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.RawGatewayEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.interactions.InteractionCallbackAction;
import net.dv8tion.jda.api.utils.data.DataObject;
import nsfw.diaper.iDiaperInteractive;
import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class lcApplicationInteractionReceive implements llCommonKeys.InteractionStructure{
    Logger logger = Logger.getLogger(getClass());

    protected lcApplicationInteractionReceive() {
        String fName = "[build]";
        logger.info(fName + ".blank");
    }
    protected lcApplicationInteractionReceive(RawGatewayEvent event) {
        String fName = "[build]";
        try {
             jda=event.getJDA();
             if(!event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                 throw  new Exception("Invalid event type");
             }
             String str=event.getPayload().toString();
             logger.info(fName+".Payload="+str);
             JSONObject jsonObject=new JSONObject(str);
             if(!jsonObject.has(llCommonKeys.keyType)){
                 throw  new Exception("key type missing");
             }
             if(jsonObject.optInt(llCommonKeys.keyType)!=2){
                 throw  new Exception("type is not of APPLICATION_COMMAND");
             }
             if(jsonObject.has(llCommonKeys.keyData)&&jsonObject.isNull(llCommonKeys.keyData)){
                 throw  new Exception("key APPLICATION_COMMAND data object is missing");
             }
             set(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    protected lcApplicationInteractionReceive(JDA jda, JSONObject jsonObject) {
        String fName = "[build]";
        try {
            this.jda=jda;
            set(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }
    public lcApplicationInteractionReceive(RawGatewayEvent event, lcGlobalHelper global) {
        String fName = "[build]";
        try {
            jda=event.getJDA();this.global=global;
            if(!event.getType().equalsIgnoreCase("INTERACTION_CREATE")){
                throw  new Exception("Invalid event type");
            }
            String str=event.getPayload().toString();
            logger.info(fName+".Payload="+str);
            JSONObject jsonObject=new JSONObject(str);
            if(!jsonObject.has(llCommonKeys.keyType)){
                throw  new Exception("key type missing");
            }
            if(jsonObject.optInt(llCommonKeys.keyType)!=2){
                throw  new Exception("type is not of APPLICATION_COMMAND");
            }
            if(jsonObject.has(llCommonKeys.keyData)&&jsonObject.isNull(llCommonKeys.keyData)){
                throw  new Exception("key APPLICATION_COMMAND data object is missing");
            }
            set(jsonObject);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

        }
    }

    public enum lcApplicationTypes {
        INVALID(-1, ""),
        CHAT_INPUT(1, "chat_input"),
        USER(2, "user"),
        MESSAGE(3, "message");
        protected String string;
        protected int code;

        private lcApplicationTypes(int code, String string) {
            this.code = code;
            this.string = string;
        }

        public static lcApplicationTypes valueByCode(int code) {
            lcApplicationTypes[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                lcApplicationTypes status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }

        public static lcApplicationTypes valueByString(String string) {
            lcApplicationTypes[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                lcApplicationTypes status = var1[var3];
                if (status.string.equals(string)) {
                    return status;
                }
            }
            return null;
        }

        public String getString() {
            return this.string;
        }

        public int getCode() {
            return this.code;
        }

        static {
            lcApplicationTypes[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                lcApplicationTypes s = var0[var2];
            }

        }

        public static String getString(iDiaperInteractive.DIAPERTYPE level) {
            String fName = "[getString]";
            Logger logger = Logger.getLogger(iDiaperInteractive.class);
            try {
                return level.getString();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }


    }
    protected int type = 0;
    protected JDA jda;
    protected lcGlobalHelper global=null;
    protected String id="",application_id="",channel_id="",token="";
    protected JSONObject jsondata=new JSONObject(),jsonmessage=new JSONObject();
    protected Guild guild=null;
    protected Member member=null;
    protected User user=null;
    protected int version=0;
    protected TextChannel textChannel=null;
    protected PrivateChannel privateChannel =null;
    protected lcApplicationInteractionReceive set(JSONObject jsonObject) {
        String fName = "[set]";
        try {

            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            String key="";

            key=Type ;if (jsonObject.has(key)&&!jsonObject.isNull(key)) type = jsonObject.optInt(key,-1);
            key=Version ;if (jsonObject.has(key)&&!jsonObject.isNull(key)) version= jsonObject.optInt(key,-1);
            key=Id;if (jsonObject.has(key)&&!jsonObject.isNull(key)) id = jsonObject.optString(key,"");
            key=ApplicationId;if (jsonObject.has(key)&&!jsonObject.isNull(key)) application_id= jsonObject.optString(key,"");
            key=GuildId;if (jsonObject.has(key)&&!jsonObject.isNull(key)) setGuild(jsonObject.optString(key,""));
            key=ChannelId;if (jsonObject.has(key)&&!jsonObject.isNull(key)) channel_id= jsonObject.optString(key,"");
            setChannel(channel_id);
            key=Token;if (jsonObject.has(key)&&!jsonObject.isNull(key)) token= jsonObject.optString(key,"");
            key=Message;if (jsonObject.has(key)&&!jsonObject.isNull(key)) jsonmessage= jsonObject.optJSONObject(key);
            key=Member;if (jsonObject.has(key)&&!jsonObject.isNull(key)) setMember(jsonObject.optJSONObject(key));
            key=User;if (jsonObject.has(key)&&!jsonObject.isNull(key)) setUser(jsonObject.optJSONObject(key));
            key=Data;if (jsonObject.has(key)&&!jsonObject.isNull(key)) jsondata= jsonObject.optJSONObject(key);
            if(jsondata==null||jsondata.isEmpty())throw  new Exception("Data object can't be null!");
            setData(jsondata);
            //setInteraction(jsonObject);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setGuild(String id) {
        String fName = "[setGuild]";
        try {
            guild=jda.getGuildById(id);
            logger.info(fName+".name="+guild.getName());
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setChannel(String id) {
        String fName = "[setChannel]";
        try {
            if(guild!=null){
                textChannel=guild.getTextChannelById(id);
                logger.info(fName+".name="+textChannel.getName());
            }else{
                privateChannel =jda.getPrivateChannelById(id);
                logger.info(fName+".name="+ privateChannel.getName());
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setMember(JSONObject jsonObject) {
        String fName = "[setMember]";
        try {

            if (jsonObject.has(User)&&!jsonObject.isNull(User)) {
                setUser(jsonObject.optJSONObject(User));
                if(user!=null){
                    member=guild.getMemberById(user.getId());
                    logger.info(fName+".name="+member.getUser().getName());
                }
            }else
            if (jsonObject.has(id)&&!jsonObject.isNull(Id)){
                String id= jsonObject.optString(Id,"");
                if(!id.isBlank()){
                    member=guild.getMemberById(id);
                    logger.info(fName+".name="+member.getUser().getName());
                }
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setUser(JSONObject jsonObject) {
        String fName = "[setUser]";
        try {
            String key="";
            String id="";
            key=Id;if (jsonObject.has(key)&&!jsonObject.isNull(key)) id = jsonObject.optString(key,"");
            if(!id.isBlank()){
                user=jda.getUserById(id);
                logger.info(fName+".name="+user.getName());
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public String getInteractionId() {
        String fName = "[getInteractionId]";
        try {
            return  id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getInteractionIdAsLong() {
        String fName = "[getInteractionIdAsLong]";
        try {
            return  Long.parseLong(id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getToken() {
        String fName = "[getToken]";
        try {
            return token;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JDA getJDA() {
        String fName = "[getJDA]";
        try {
            return  jda;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcGlobalHelper getGlobal() {
        String fName = "[getGlobal]";
        try {
            return  global;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public User getUser() {
        String fName = "[getUser]";
        try {
            return  user;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember() {
        String fName = "[getMember]";
        try {
            return  member;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public boolean isFromGuild() {
        String fName = "[isFromGuild]";
        try {
            return  guild!=null;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public Guild getGuild() {
        String fName = "[getGuild]";
        try {
            return  guild;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public String getChannelId() {
        String fName = "[getChannelId]";
        try {
            return  channel_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getChannelIdAsLong() {
        String fName = "[getChannelIdAsLong]";
        try {
            return  Long.parseLong(channel_id);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public TextChannel getTextChannel() {
        String fName = "[getTextChannel]";
        try {
            return  textChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public PrivateChannel getPrivateChannel() {
        String fName = "[getrivateChannel]";
        try {
            return privateChannel;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public JSONObject getData() {
        String fName = "[getData]";
        try {
            return  jsondata;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setData(JSONObject jsonObject) {
        String fName = "[setData]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            if(jsonObject.has(InteractionDataStructure.Id))commandId=jsonObject.optString(InteractionDataStructure.Id,"");
            if(jsonObject.has(InteractionDataStructure.Name))commandName=jsonObject.optString(InteractionDataStructure.Name,"");
            if(jsonObject.has(InteractionDataStructure.Resolved))setResolved(jsonObject.optJSONObject(InteractionDataStructure.Resolved));
            else if(jsonObject.has("resolved")){logger.info("key=resolved");setResolved(jsonObject.optJSONObject("resolved"));}
            if(jsonObject.has(InteractionDataStructure.Options))setOptions(jsonObject.optJSONArray(InteractionDataStructure.Options));
            switch (jsonObject.optInt(llCommonKeys.keyType)){
                case 1:
                    logger.info(fName + ".type=slash");
                    this.type = lcApplicationTypes.CHAT_INPUT.getCode();
                    //slashCommand=new SlashCommand(jsonObject);
                case 2:
                    logger.info(fName + ".type=user");
                    this.type = lcApplicationTypes.USER.getCode();
                    userCommand=new lUserCommand(this);
                    break;
                case 3:
                    logger.info(fName + ".type=message");
                    this.type = lcApplicationTypes.MESSAGE.getCode();
                    messageCommand=new lMessageCommand(this);
                    break;
                default:
                    throw  new Exception("Invalid application type!");
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lcApplicationTypes getType() {
        String fName = "[getType]";
        try {
            logger.info(fName + ".value=" + type);
            return lcApplicationTypes.valueByCode(type);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return lcApplicationTypes.INVALID;
        }
    }
    public int getTypeAsInt() {
        String fName = "[getTypeAsInt]";
        try {
            logger.info(fName + ".value=" + type);
            return type;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isSlashCommand() {
        String fName = "[isSlashCommand]";
        try {
            if (getType() == lcApplicationTypes.CHAT_INPUT) {
                logger.info(fName + ".true");
                return true;
            } else {
                logger.info(fName + ".false");
                return false;
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isUserCommand() {
        String fName = "[isUserCommand]";
        try {
            if (getType() == lcApplicationTypes.USER) {
                logger.info(fName + ".true");
                return true;
            } else {
                logger.info(fName + ".false");
                return false;
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean isMessageCommand() {
        String fName = "[isMessageCommand]";
        try {
            if (getType() == lcApplicationTypes.MESSAGE) {
                logger.info(fName + ".true");
                return true;
            } else {
                logger.info(fName + ".false");
                return false;
            }
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    protected lcApplicationInteractionReceive setResolved(JSONObject jsonObject) {
        String fName = "[setResolved]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            if(jsonObject.has(llCommonKeys.keyUsers)){
               setResolvedUsers(jsonObject.optJSONObject(llCommonKeys.keyUsers));
            }
            if(jsonObject.has(llCommonKeys.keyRoles)){
                setResolvedRoles(jsonObject.optJSONObject(llCommonKeys.keyRoles));
            }
            if(jsonObject.has(llCommonKeys.keyMembers)){
                setResolvedMembers(jsonObject.optJSONObject(llCommonKeys.keyMembers));
            }
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setResolvedUsers(JSONObject jsonObject) {
        String fName = "[setResolvedUsers]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            Iterator<String>keys=jsonObject.keys();
            while(keys.hasNext()){
                String key=keys.next();
                User user=jda.getUserById(key);
                if(user!=null){
                    users.add(user);
                }
            }
            logger.info(fName + "users.size=" + users.size());
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setResolvedMembers(JSONObject jsonObject) {
        String fName = "[setResolvedMembers]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            Iterator<String>keys=jsonObject.keys();
            while(keys.hasNext()){
                String key=keys.next();
                Member member=guild.getMemberById(key);
                if(member!=null){
                    members.add(member);
                }
            }
            logger.info(fName + "members.size=" + members.size());
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setResolvedRoles(JSONObject jsonObject) {
        String fName = "[setResolvedRoles]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            Iterator<String>keys=jsonObject.keys();
            while(keys.hasNext()){
                String key=keys.next();
                Role role=guild.getRoleById(key);
                if(role!=null){
                    roles.add(role);
                }
            }
            logger.info(fName + "roles.size=" + roles.size());
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setOptions(JSONArray jsonArray) {
        String fName = "[setOptions]";
        try {
            logger.info(fName + ".jsonArray=" + jsonArray.toString());
            for(int i=0;i<jsonArray.length();i++){
                options.add(new lOption(jsonArray.getJSONObject(i)));
            }
            logger.info(fName + ".options.size=" + options.size());
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lcApplicationInteractionReceive setInteraction(JSONObject jsonObject) {
        String fName = "[setInteraction]";
        try {
            logger.info(fName + ".jsonObject=" + jsonObject.toString());
            DataObject dataObject=DataObject.fromJson(jsonObject.toString());
            MessageChannel messageChannel=textChannel;
            interaction=new dev.cerus.jdasc.interaction.Interaction(token,getInteractionIdAsLong(), InteractionType.APPLICATION_COMMAND,getCommandIdAsLong(),commandName,guild,messageChannel,member,null,null,null);
            return this;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Interaction getInteraction() {
        String fName = "[getInteraction]";
        try {
            return interaction;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected List<lOption>options=new ArrayList<>();
    protected List<Member>members=new ArrayList<>();
    protected List<User>users=new ArrayList<>();
    protected List<Role>roles=new ArrayList<>();
    public List<User> getUsers() {
        String fName = "[getUsers]";
        try {
            return  users;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Member> getMembers() {
        String fName = "[getMembers]";
        try {
            return  members;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<Role> getRoles() {
        String fName = "[getRoles]";
        try {
            return  roles;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public List<lOption> getOptions() {
        String fName = "[getOptions]";
        try {
            return options;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public User getUser(int index) {
        String fName = "[getUser]";
        try {
            logger.info(fName+".index="+index);
            return  users.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Member getMember(int index) {
        String fName = "[getMember]";
        try {
            logger.info(fName+".index="+index);
            return  members.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Role getRole(int index) {
        String fName = "[getRole]";
        try {
            logger.info(fName+".index="+index);
            return  roles.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lOption getOption(int index) {
        String fName = "[getOption]";
        try {
            logger.info(fName+".index="+index);
            return options.get(index);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public int sizeUsers() {
        String fName = "[sizeUsers]";
        try {
            return  users.size();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int sizeMembers() {
        String fName = "[sizeMembers]";
        try {
            return  members.size();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int sizeRoles() {
        String fName = "[sizeRoles]";
        try {
            return  roles.size();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public int sizeOptions() {
        String fName = "[sizeOptions]";
        try {
            return options.size();
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public boolean isEmptyUsers() {
        String fName = "[isEmptyUsers]";
        try {
            boolean resolt=users.isEmpty();
            logger.info(fName+"isEmpty="+resolt);
            return  resolt;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isEmptyMembers() {
        String fName = "[isEmptyMembers]";
        try {
            boolean resolt=members.isEmpty();
            logger.info(fName+"isEmpty="+resolt);
            return  resolt;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isEmptyRoles() {
        String fName = "[sisEmptyRoles]";
        try {
            boolean resolt=roles.isEmpty();
            logger.info(fName+"isEmpty="+resolt);
            return  resolt;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    public boolean isEmptyeOptions() {
        String fName = "[isEmptyOptions]";
        try {
            boolean resolt=options.isEmpty();
            logger.info(fName+"isEmpty="+resolt);
            return  resolt;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return true;
        }
    }
    protected String commandId="",commandName="";
    public String getCommandId() {
        String fName = "[getcommandId]";
        try {
            return  commandId;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public long getCommandIdAsLong() {
        String fName = "[getCommandIdAsLong]";
        try {
            return  Long.parseLong(commandId);
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getCommandName() {
        String fName = "[getcommandName]";
        try {
            return  commandName;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected lSlashCommand slashCommand=null;
    protected lUserCommand userCommand=null;
    protected lMessageCommand messageCommand=null;
    public int getVersion() {
        String fName = "[getVersion]";
        try {
            return version;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return 0;
        }
    }
    public String getApplicationId() {
        String fName = "[getApplicationId]";
        try {
            return application_id;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return "";
        }
    }
    public JSONObject getMessageJson() {
        String fName = "[getMessageJson]";
        try {
            return jsonmessage;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public JSONObject getDataJson() {
        String fName = "[getDataJson]";
        try {
            return jsondata;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new JSONObject();
        }
    }
    public static class lOption extends lcApplicationInteractionReceive {
        Logger logger = Logger.getLogger(getClass());
        String nName="Option@";
        protected String name="";
        protected int type=0;
        protected OptionType optionType=OptionType.UNKNOWN;
        protected String valueStr="";
        protected long valueInteger =0;
        protected double valueNumber =0;
        protected List<lOption>options=new ArrayList<>();
        protected lOption(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        protected lOption(JSONObject jsonObject){
            set(jsonObject);
        }
        protected lOption set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                String key = "";
                key = InteractionDataStructure.OptionStructure.Type;
                if (jsonObject.has(key) && !jsonObject.isNull(key)) setType(jsonObject.optInt(key, -1));
                key = InteractionDataStructure.OptionStructure.Name;
                if (jsonObject.has(key) && !jsonObject.isNull(key)) name = jsonObject.optString(key, "");
                key = InteractionDataStructure.OptionStructure.Value;
                if (jsonObject.has(key) && !jsonObject.isNull(key)) {
                    switch (optionType){
                        case NUMBER:
                            valueNumber =jsonObject.optDouble(key);
                            valueStr=Double.toString(valueNumber);
                            valueInteger=(long) valueNumber;
                            break;
                        case INTEGER:
                            valueInteger=jsonObject.optLong(key);
                            valueStr=Long.toString( valueInteger);
                            valueNumber = valueInteger;
                            break;
                        case STRING:
                            valueStr=jsonObject.optString(key,"");
                            break;
                    }
                }
                key = InteractionDataStructure.OptionStructure.Options;
                if (jsonObject.has(key) && !jsonObject.isNull(key)) setOptions(jsonObject.optJSONArray(key));
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        protected lOption setType(int input) {
            String fName = "[setType]";
            try {
                this.type=input;
                OptionType type=OptionType.fromKey(input);
                if(type==null)type=OptionType.UNKNOWN;
                logger.info(nName+fName+".type="+type.name());
                optionType=type;
                return  this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        protected lOption setOptions(JSONArray jsonArray) {
             String fName = "[setOptions]";
             try {
                 logger.info(fName + ".jsonArray=" + jsonArray.toString());
                 for(int i=0;i<jsonArray.length();i++){
                     options.add(new lOption(jsonArray.getJSONObject(i)));
                 }
                 return this;
             } catch (Exception e) {
                    logger.error(fName + ".exception=" + e);
                    logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                    return null;
                }
        }
        public List<lOption> getOptions() {
            String fName = "[getOptions]";
            try {
                return  options;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lOption getOption(int index) {
            String fName = "[getOption]";
            try {
                logger.info(fName+".index="+index);
                return  options.get(index);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean isEmptyOptions() {
            String fName = "[isEmptyOptions]";
            try {
                boolean l=options.isEmpty();
                logger.info(fName+".size="+l);
                return  l;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public int sizeOptions() {
            String fName = "[sizeOptions]";
            try {
                int l=options.size();
                logger.info(fName+".size="+l);
                return  l;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public int getOptionTypeAsInt() {
            String fName = "[getOptionTypeAsInt]";
            try {
                logger.info(fName+".value="+type);
                return  type;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public OptionType getOptionType() {
            String fName = "[getOptionType]";
            try {
                OptionType optionType=this.optionType;
                return  optionType;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return OptionType.UNKNOWN;
            }
        }
        public boolean canOptionSupportChoices() {
            String fName = "[canOptionSupportChoices]";
            try {
                boolean result=getOptionType().canSupportChoices();
                logger.info(nName+fName+".result="+result);
                return result;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public long getValueAsInteger() {
            String fName = "[getValueAsInteger]";
            try {
                logger.info(fName+".value="+ valueInteger);
                return valueInteger;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public double getValueAsNumber() {
            String fName = "[getValueAsNumber]";
            try {
                logger.info(fName+".value="+ valueNumber);
                return valueNumber;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getValueAsString() {
            String fName = "[getValueAsString]";
            try {
                logger.info(fName+".value="+valueStr);
                return  valueStr;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }
    }
    public static class lSlashCommand extends lcApplicationInteractionReceive {
        Logger logger = Logger.getLogger(getClass());
        String nName="Slash@";
        protected lSlashCommand(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
    }
    public static class lUserCommand extends lcApplicationInteractionReceive {
        Logger logger = Logger.getLogger(getClass());
        String nName="UserCommand@";
        protected lUserCommand(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        public lUserCommand(lcApplicationInteractionReceive father){
            String fName = "[set]";
            try {
                userCommand=this;
                jda=father.getJDA();global=father.getGlobal();
                type =father.getTypeAsInt();
                version= father.getVersion();
                id=father.getInteractionId();
                application_id=father.getApplicationId();
                guild=father.getGuild();
                channel_id= father.getChannelId();
                textChannel=father.getTextChannel();
                privateChannel=father.getPrivateChannel();
                token= father.getToken();
                jsonmessage= father.getMessageJson();
                member=father.getMember();
                user=father.getUser();
                jsondata=father.getDataJson();

                commandId=father.getCommandId();
                commandName=father.getCommandName();
                roles=father.getRoles();
                users=father.getUsers();
                members=father.getMembers();
                options=father.getOptions();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }

        }
        protected lUserCommand set(JSONObject jsonObject) {
            String fName = "[set]";
            try {
                jda.getUserById("");
                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                String key="";
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName = "[getId]";
            try {
                return  commandId;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong() {
            String fName = "[getIdAsLong]";
            try {
                return  Long.parseLong(commandId);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getName() {
            String fName = "[getName]";
            try {
                return  commandName;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcApplicationInteractionReceive getApp() {
            String fName = "[getApp]";
            try {
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }
    public static class lMessageCommand extends lcApplicationInteractionReceive {
        Logger logger = Logger.getLogger(getClass());
        String nName="MessageCommand@";
        protected lMessageCommand(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        public lMessageCommand(lcApplicationInteractionReceive father){
            String fName = "[set]";
            try {
                messageCommand=this;
                jda=father.getJDA();global=father.getGlobal();
                type =father.getTypeAsInt();
                version= father.getVersion();
                id=father.getInteractionId();
                application_id=father.getApplicationId();
                guild=father.getGuild();
                channel_id= father.getChannelId();
                textChannel=father.getTextChannel();
                privateChannel=father.getPrivateChannel();
                token= father.getToken();
                jsonmessage= father.getMessageJson();
                member=father.getMember();
                user=father.getUser();
                jsondata=father.getDataJson();

                commandId=father.getCommandId();
                commandName=father.getCommandName();
                roles=father.getRoles();
                users=father.getUsers();
                members=father.getMembers();
                options=father.getOptions();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        protected lMessageCommand set(JSONObject jsonObject) {
            String fName = "[set]";
            try {

                logger.info(fName + ".jsonObject=" + jsonObject.toString());
                String key="";
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getId() {
            String fName = "[getId]";
            try {
                return  getCommandId();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public long getIdAsLong() {
            String fName = "[getIdAsLong]";
            try {
                return  Long.parseLong(getCommandId());
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public String getName() {
            String fName = "[getName]";
            try {
                return  getCommandName();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcApplicationInteractionReceive getApp() {
            String fName = "[getApp]";
            try {
                return this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

    }
    public lUserCommand getUserCommand() {
        String fName = "[getUserCommand]";
        try {
            return  userCommand;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lUserCommand getMessageCommand() {
        String fName = "[getMessageCommand]";
        try {
            return  userCommand;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    protected  boolean acknowledged =false;
    Interaction interaction=null;

    //https://discord.com/developers/docs/interactions/receiving-and-responding#interaction-response-object-interaction-callback-type
    public lInteractionResponse respondWithPing(){
        String fName="[respondWithPing]";
        try {
            return  new lInteractionResponse(this,1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lInteractionResponse respondWithMessage(lcMyMessageJsonBuilder messageBuilder, boolean ephemera){
        String fName="[respondWithMessage]";
        try {
            return  new lInteractionResponse(this,messageBuilder,ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lInteractionResponse respondWithMessage(String content, boolean ephemera){
        String fName="[respondWithMessage]";
        try {
            return  new lInteractionResponse(this,content,ephemera);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public lInteractionResponse respondWithAck(){
        String fName="[respondWithAck]";
        try {
            return  new lInteractionResponse(this,1);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    public static class lInteractionResponse implements llCommonKeys.MessageStructureGet{
        private Logger logger = Logger.getLogger(lcApplicationInteractionReceive.class);
        private String nName="Respond@";
        private lInteractionResponse(){
            String fName="[build]";
            logger.info(nName+fName+".blank");
        }
        private lcGlobalHelper global=null;
        private JDA jda=null;
        private lcApplicationInteractionReceive father=null;
        private lUserCommand userCommand=null;
        private lMessageCommand messageCommand=null;
        public JDA getJDA() {
            String fName = "[getJDA]";
            try {
                return  jda;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcGlobalHelper getGlobal() {
            String fName = "[getGlobal]";
            try {
                return  global;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse getIteraction() {
            String fName = "[getIteraction]";
            try {
                return  this;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcApplicationInteractionReceive getApplicationInteraction() {
            String fName = "[getApplicationInteraction]";
            try {
                return  father;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lUserCommand getUserCommand() {
            String fName = "[getUserCommand]";
            try {
                return  userCommand;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lMessageCommand getMessageCommand() {
            String fName = "[getIteraction]";
            try {
                return  messageCommand;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse(lcApplicationInteractionReceive father) {
            String fName="[build]";
            try {
                this.father=father;
                set();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lUserCommand userCommand) {
            String fName="[build]";
            try {
                this.userCommand=userCommand;
                father=userCommand.getApp();
                set();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lMessageCommand messageCommand) {
            String fName="[build]";
            try {
                this.messageCommand=messageCommand;
                father=messageCommand.getApp();
                set();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lcApplicationInteractionReceive father, int type) {
            String fName="[build]";
            try {
                this.father=father;
                set();
                this.type=type;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lUserCommand userCommand, int type) {
            String fName="[build]";
            try {
                this.userCommand=userCommand;
                father=userCommand.getApp();
                set();
                this.type=type;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lMessageCommand messageCommand, int type) {
            String fName="[build]";
            try {
                this.messageCommand=messageCommand;
                father=messageCommand.getApp();
                set();
                this.type=type;
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lcApplicationInteractionReceive father, String content) {
            String fName="[build]";
            try {
                this.father=father;
                set();
                this.messageBuilder.setContent(content);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lUserCommand userCommand, String content) {
            String fName="[build]";
            try {
                this.userCommand=userCommand;
                father=userCommand.getApp();
                set();
                this.messageBuilder.setContent(content);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lMessageCommand messageCommand, String content) {
            String fName="[build]";
            try {
                this.messageCommand=messageCommand;
                father=messageCommand.getApp();
                set();
                this.messageBuilder.setContent(content);
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lcApplicationInteractionReceive father, lcMyMessageJsonBuilder messageBuilder) {
            String fName="[build]";
            try {
                this.father=father;
                set();
                if(messageBuilder!=null) this.messageBuilder=messageBuilder;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lUserCommand userCommand, lcMyMessageJsonBuilder messageBuilder) {
            String fName="[build]";
            try {
                this.userCommand=userCommand;
                father=userCommand.getApp();
                set();
                if(messageBuilder!=null) this.messageBuilder=messageBuilder;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lMessageCommand messageCommand, lcMyMessageJsonBuilder messageBuilder) {
            String fName="[build]";
            try {
                logger.info(nName+fName);
                this.messageCommand=messageCommand;
                father=messageCommand.getApp();
                set();
                if(messageBuilder!=null) this.messageBuilder=messageBuilder;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lcApplicationInteractionReceive father, String content, boolean ephemera) {
            String fName="[build]";
            try {
                logger.info(nName+fName);
                this.father=father;
                set();
                this.messageBuilder.setContent(content);
                this.ephemera =ephemera;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lUserCommand userCommand, String content, boolean ephemera) {
            String fName="[build]";
            try {
                this.userCommand=userCommand;
                father=userCommand.getApp();
                set();
                this.messageBuilder.setContent(content);
                this.ephemera =ephemera;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lMessageCommand messageCommand, String content, boolean ephemera) {
            String fName="[build]";
            try {
                this.messageCommand=messageCommand;
                father=messageCommand.getApp();
                set();
                this.messageBuilder.setContent(content);
                this.ephemera =ephemera;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lcApplicationInteractionReceive father, lcMyMessageJsonBuilder messageBuilder, boolean ephemera) {
            String fName="[build]";
            try {
                this.father=father;
                set();
                if(messageBuilder!=null) this.messageBuilder=messageBuilder;
                this.ephemera =ephemera;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lUserCommand userCommand, lcMyMessageJsonBuilder messageBuilder, boolean ephemera) {
            String fName="[build]";
            try {
                this.userCommand=userCommand;
                father=userCommand.getApp();
                set();
                if(messageBuilder!=null) this.messageBuilder=messageBuilder;
                this.ephemera =ephemera;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        public lInteractionResponse(lMessageCommand messageCommand, lcMyMessageJsonBuilder messageBuilder, boolean ephemera) {
            String fName="[build]";
            try {
                this.messageCommand=messageCommand;
                father=messageCommand.getApp();
                set();
                if(messageBuilder!=null) this.messageBuilder=messageBuilder;
                this.ephemera =ephemera;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));

            }
        }
        private lInteractionResponse set() {
            String fName = "[set]";
            try {
                logger.info(nName+fName);
                this.jda=father.getJDA();this.global=father.getGlobal();
                this.acknowledged=father.acknowledged;
                this.token=father.getToken();this.id=father.getInteractionId();
                return this;
            } catch (Exception e) {
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        private String id="",token="";
        private  int type=InteractionCallbackAction.ResponseType.CHANNEL_MESSAGE_WITH_SOURCE.getRaw();
        private boolean ephemera =false, acknowledged =false;
        private lcMyMessageJsonBuilder messageBuilder= new lcMyMessageJsonBuilder();
        public lInteractionResponse setType(int type) {
            String fName="[setEmbed]";
            try {
                logger.info(nName+fName+"input="+type);
                this.type=type;
                return this;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setType(InteractionCallbackAction.ResponseType type) {
            String fName="[setType]";
            try {
                logger.info(nName+fName+"input="+type.getRaw());
                this.type=type.getRaw();
                return this;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setEphemera(boolean ephemera) {
            String fName="[setEmbed]";
            try {
                logger.info(nName+fName+"input="+ephemera);
                this.ephemera=ephemera;
                return this;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public InteractionCallbackAction.ResponseType getType() {
            String fName="[getType]";
            try {
                InteractionCallbackAction.ResponseType r=null;
                switch (getTypeAsInt()){
                    case 4:r=InteractionCallbackAction.ResponseType.CHANNEL_MESSAGE_WITH_SOURCE;break;
                    case 5:r=InteractionCallbackAction.ResponseType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE;break;
                    case 6:r=InteractionCallbackAction.ResponseType.DEFERRED_MESSAGE_UPDATE;break;
                    case 7:r=InteractionCallbackAction.ResponseType.MESSAGE_UPDATE;break;
                }
                return r;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public int getTypeAsInt() {
            String fName="[getTypeAsInt]";
            try {
                logger.info(nName+fName+"value="+type);
                return type;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return 0;
            }
        }
        public boolean isAcknowledged() {
            String fName="[isAcknowledged]";
            try {
                logger.info(nName+fName+"value="+acknowledged);
                return acknowledged;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean isEphemera() {
            String fName="[isEphemera]";
            try {
                logger.info(nName+fName+"value="+ephemera);
                return ephemera;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }

        public lInteractionResponse setEmbed(@Nullable MessageEmbed embed) {
            String fName="[setEmbed]";
            try {
                return messageBuilder.setEmbed(embed)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setEmbed(@Nullable EmbedBuilder embed) {
            String fName="[setEmbed]";
            try {
                return messageBuilder.setEmbed(embed)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setEmbed(@Nullable JSONObject embed) {
            String fName="[setEmbed]";
            try {
                return messageBuilder.setEmbed(embed)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setEmbed(@Nullable lcMyEmbedBuilder embed) {
            String fName="[setEmbed]";
            try {
                return messageBuilder.setEmbed(embed)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse clearEmbed() {
            String fName="[clearEmbed]";
            try {
                return messageBuilder.clearEmbed()!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getEmbedJson() {
            String fName="[getEmbedJson]";
            try {
                return messageBuilder.getEmbedJson();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMyEmbedBuilder getMyEmbed() {
            String fName="[getMyEmbed]";
            try {
                return messageBuilder.getMyEmbed();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public EmbedBuilder getEmbedBuilder() {
            String fName="[getEmbedBuilder]";
            try {
                return messageBuilder.getEmbedBuilder();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public MessageEmbed getEmbed() {
            String fName="[getEmbed]";
            try {
                return messageBuilder.getEmbed();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public lInteractionResponse setAllowedMentions(@Nullable lcMyMessageAllowedMentionsBuilder allowedMentions) {
            String fName="[setAllowedMentions]";
            try {
                return messageBuilder.setAllowedMentions(allowedMentions)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setAllowedMentions(@Nullable JSONObject allowedMentions) {
            String fName="[setAllowedMentions]";
            try {
                return messageBuilder.setAllowedMentions(allowedMentions)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse clearAllowedMentions() {
            String fName="[clearAllowedMentions]";
            try {
                return messageBuilder.clearAllowedMentions()!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getAllowedMentionsJson() {
            String fName="[getAllowedMentionsJson]";
            try {
                return messageBuilder.getAllowedMentionsJson();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMyMessageAllowedMentionsBuilder  getAllowedMentions() {
            String fName="[getAllowedMentions]";
            try {
                return messageBuilder.getAllowedMentions();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public lInteractionResponse setMessageReference(@Nullable lcMyMessageReferenceBuilder messageReference) {
            String fName="[setMessageReference]";
            try {
                return messageBuilder.setMessageReference(messageReference)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setMessageReference(@Nullable JSONObject messageReference) {
            String fName="[setMessageReference]";
            try {
                return messageBuilder.setMessageReference(messageReference)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONObject getMessageReferenceJson() {
            String fName="[getMessageReferenceJson]";
            try {
                return messageBuilder.getMessageReferenceJson();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse clearMessageReference() {
            String fName="[clearMessageReference]";
            try {
                return messageBuilder.clearMessageReference()!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMyMessageReferenceBuilder  getMessageReference() {
            String fName="[getMessageReference]";
            try {
                return messageBuilder.getMessageReference();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public lInteractionResponse setContent(@Nullable String content) {
            String fName="[setContent]";
            try {
                return messageBuilder.setContent(content)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse clearContent() {
            String fName="[clearAllowedMentions]";
            try {
                return messageBuilder.clearContent()!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getContent() {
            String fName="[getContent]";
            try {
                return messageBuilder.getContent();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }

        public lInteractionResponse addComponent(@Nullable lcMessageBuildComponent component) {
            String fName="[addComponent]";
            try {
                return messageBuilder.addComponent(component)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent remComponent(int index) {
            String fName="[remComponent]";
            try {
                return messageBuilder.remComponent(index);
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lcMessageBuildComponent getComponent(int index) {
            String fName="[getComponent]";
            try {
                return messageBuilder.getComponent(index);
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setComponent(int index,lcMessageBuildComponent component) {
            String fName="[setComponent]";
            try {
                return messageBuilder.setComponent(index,component)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setComponents(@Nullable lcMessageBuildComponents components) {
            String fName="[setComponents]";
            try {
                return messageBuilder.setComponents(components)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse addComponents(@Nullable List<lcMessageBuildComponent> components) {
            String fName="[addComponents]";
            try {
                return messageBuilder.addComponents(components)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setComponents(@Nullable List<lcMessageBuildComponent> components) {
            String fName="[setComponents]";
            try {
                return messageBuilder.setComponents(components)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse addComponents(@Nullable JSONArray components) {
            String fName="[addComponents]";
            try {

                return messageBuilder.addComponents(components)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse setComponents(@Nullable JSONArray components) {
            String fName="[setComponents]";
            try {
                return messageBuilder.setComponents(components)!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public lInteractionResponse clearComponents() {
            String fName="[clearComponents]";
            try {
                return messageBuilder.clearComponents()!=null?this:null;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public JSONArray getComponentsJson() {
            String fName="[getComponentsJson]";
            try {
                return messageBuilder.getComponentsJson();
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public List<lcMessageBuildComponent> getComponents() {
            String fName="[getComponents]";
            try {
                return messageBuilder.getComponents();
            }catch (Exception e){
                return null;
            }
        }


        private HttpResponse<JsonNode> lastJsonResponse;
        private int interactioncallbacktype =0;
        protected lcApplicationInteractionMessage message=null;
        public boolean didWeGetMessage4(RawGatewayEvent event){
            String fName="[didWeGetMessage4]";
            try {
                if(!event.getType().equalsIgnoreCase("MESSAGE_CREATE")){
                    return false;
                }
                if(event.getPayload().getInt("type")!=20&&event.getPayload().getInt("type")!=19){
                    return false;
                }
                JSONObject jsonObject=new JSONObject(event.getPayload().toString());
                logger.info(nName+fName+".Payload="+jsonObject.toString());
                if(!jsonObject.has(keyAuthor)){
                    return false;
                }
                if(!jsonObject.getJSONObject(keyAuthor).optString(keyId).equalsIgnoreCase(jda.getSelfUser().getId())){
                    return false;
                }
                /*if(!jsonObject.has(keyInteraction)){
                    return false;
                }
                if(!jsonObject.getJSONObject(keyInteraction).optString(keyId).equalsIgnoreCase(father.id)){
                    return false;
                }*/
                if(!jsonObject.has(keyApplicationId)){
                    return false;
                }
                if(!jsonObject.optString(keyApplicationId,"").equalsIgnoreCase(father.application_id)){
                    return false;
                }
                return true;
            }catch (Exception e3){
                logger.error(nName+fName + ".exception=" + e3);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e3.getStackTrace()));
                return false;
            }
        };
        public lcApplicationInteractionMessage send(){
            String fName="[send]";
            try {
                lcApplicationInteractionReceive.lInteractionResponse response=this;
                String url= "";
                boolean original=false;
                JSONObject jsonObject=new JSONObject();
                if(isAcknowledged()){
                    url= "https://discord.com/api/v8/webhooks/"+father.application_id+"/"+father.token;
                    jsonObject=messageBuilder.getJson();
                    if(ephemera) jsonObject.put("flags",64);
                }else{
                    url= "https://discord.com/api/v8/interactions/"+father.id+"/"+father.token+"/callback";
                    jsonObject.put("type",type);
                    jsonObject.put("data",messageBuilder.getJson());
                    if(ephemera) jsonObject.getJSONObject("data").put("flags",64);
                    original=true;

                }
                logger.info(nName+fName+".url="+url);
                logger.info(nName+fName+".json"+jsonObject.toString());
                JSONObject finalJsonObject = jsonObject;
                if(!isAcknowledged()){
                    boolean finalOriginal = original;
                    global.waiter.waitForEvent(RawGatewayEvent.class,
                            e -> (
                                    didWeGetMessage4(e)
                            ),
                            e -> {
                                try {
                                    message=new lcApplicationInteractionMessage(e,response, finalJsonObject,finalOriginal);
                                }catch (Exception e3){
                                    logger.error(nName+fName + ".exception=" + e3);
                                    logger.error(nName+fName + ".exception:" + Arrays.toString(e3.getStackTrace()));

                                }
                            },5, TimeUnit.MINUTES, null);
                }


                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asJson();
                lastJsonResponse=jsonResponse;
                logger.info(nName+fName+".status ="+jsonResponse.getStatus());
                JSONObject body=new JSONObject();
                try {
                    body=jsonResponse.getBody().getObject();
                    logger.info(nName+fName+".body ="+body.toString());
                }catch (Exception e){
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(jsonResponse.getStatus()>299){
                    throw  new Exception("Invalid status");
                }
                logger.info(nName+fName+".got");
                if(!isAcknowledged()){
                    acknowledged =true;
                    father.acknowledged=true;
                    interactioncallbacktype=type;
                    int c=0;
                    while (message == null&&c<10){
                        Thread.sleep(1000);
                        c++;
                    }
                    if(message==null){
                        throw  new Exception("Could not return message!");
                    }
                    logger.info(nName+fName +".got message original");
                    return message;
                }
                message=new lcApplicationInteractionMessage(response,messageBuilder.getJson(),body);
                logger.info(nName+fName +".got message followup");
                return message;
            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public boolean respondWithAck(){
            String fName="[respondWithAck]";
            try {
                int type= InteractionCallbackAction.ResponseType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE.getRaw();
                if(acknowledged)throw  new Exception("isAcknowledged");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("type",type);
                logger.info(nName+fName+".json"+jsonObject.toString());
                String url= "https://discord.com/api/v8/interactions/"+id+"/"+token+"/callback";
                logger.info(nName+fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asJson();
                lastJsonResponse=jsonResponse;
                logger.info(nName+fName+".status ="+jsonResponse.getStatus());
                JSONObject body=new JSONObject();
                try {
                    body=jsonResponse.getBody().getObject();
                    logger.info(nName+fName+".body ="+body.toString());
                }catch (Exception e){
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(jsonResponse.getStatus()>299){
                    logger.warn(nName+fName+".invalid status");return false;
                }else{
                    logger.info(nName+fName+".got");
                    interactioncallbacktype =InteractionCallbackAction.ResponseType.DEFERRED_CHANNEL_MESSAGE_WITH_SOURCE.getRaw();;
                    return true;
                }

            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public boolean respondWithPing(){
            String fName="[respondWithPing]";
            try {

                int type=1;
                if(acknowledged)throw  new Exception("isAcknowledged");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("type",type);
                logger.info(nName+fName+".json"+jsonObject.toString());
                String url= "https://discord.com/api/v8/interactions/"+id+"/"+token+"/callback";
                logger.info(nName+fName+".url="+url);
                Unirest a= new Unirest();
                a.config().verifySsl(false);
                HttpResponse<JsonNode> jsonResponse =a.post(url)
                        .header("accept", "application/json")
                        .header("Content-Type", "application/json")
                        .body(jsonObject.toString())
                        .asJson();
                lastJsonResponse=jsonResponse;
                logger.info(nName+fName+".status ="+jsonResponse.getStatus());
                JSONObject body=new JSONObject();
                try {
                    body=jsonResponse.getBody().getObject();
                    logger.info(nName+fName+".body ="+body.toString());
                }catch (Exception e){
                    logger.error(nName+fName + ".exception=" + e);
                    logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                }
                if(jsonResponse.getStatus()>299){
                    logger.warn(nName+fName+".invalid status");return false;
                }else{
                    logger.info(nName+fName+".got");
                    interactioncallbacktype =1;
                    return true;
                }

            }catch (Exception e){
                logger.error(nName+fName + ".exception=" + e);
                logger.error(nName+fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return false;
            }
        }
        public String getInteractionId() {
            String fName = "[getInteractionId]";
            try {
                return  father.getInteractionId();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
        public String getToken() {
            String fName = "[getToken]";
            try {
                return father.getToken();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return null;
            }
        }
    }

}

