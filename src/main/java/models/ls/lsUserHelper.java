package models.ls;

import models.lcGlobalHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.annotations.DeprecatedSince;
import net.dv8tion.jda.annotations.ForRemoval;
import net.dv8tion.jda.annotations.ReplaceWith;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.internal.utils.Checks;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.*;

public interface lsUserHelper
{
    static User lsRetrieveUserById(JDA jda, long id){
        String fName="[lsRetrieveUserById]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return jda.retrieveUserById(id).complete();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsRetrieveUserById(List<JDA>jdas, long id){
        String fName="[lsRetrieveUserById]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            User user=null;
            for(int i=0;i<jdas.size();i++){
                JDA jda=jdas.get(i);
                user= lsRetrieveUserById(jda,id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUserById(JDA jda, long id){
        String fName="[lsGetUserById]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            return jda.getUserById(id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static User lsGetUserById(List<JDA>jdas, long id){
        String fName="[lsGetUserById]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            User user=null;
            for(int i=0;i<jdas.size();i++){
                JDA jda=jdas.get(i);
                user= lsGetUserById(jda,id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUser(Guild guild, long id){
        String fName="llGetUser.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsGetUserById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsRetrieveUserById(Guild guild, long id){
        String fName="lsRetrieveUserById.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsRetrieveUserById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUserById(Guild guild, long id){
        String fName="lsGetUserById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            return lsGetUserById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static User lsGetUser(lcGlobalHelper global, long id){
        String fName="llGetUser.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            User user=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                user= lsGetUserById(jda, id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsRetrieveUserById(lcGlobalHelper global, long id){
        String fName="lsRetrieveUserById.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            User user=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                user=lsRetrieveUserById(jda, id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUserById(lcGlobalHelper global, long id){
        String fName="lsGetUserById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            User user=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                user=lsGetUserById(jda, id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static User lsGetUser(JDA jda, String id){
        String fName="llGetUser.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsGetUserById(jda,id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsRetrieveUserById(JDA jda, String id){
        String fName="lsRetrieveUserById.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            id=id.replace("<@","").replace("<!@","").replace(">","");
            id=id.replace("(@","").replace("(!@","").replace(")","");
            id=id.replace("!","");logger.info(fName+"id="+id);
            return jda.retrieveUserById(id).complete();
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUserById(JDA jda, String id){
        String fName="lsGetUserById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            id=id.replace("<@","").replace("<!@","").replace(">","");
            id=id.replace("(@","").replace("(!@","").replace(")","");
            id=id.replace("!","");logger.info(fName+"id="+id);
            return jda.getUserById(id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static User lsGetUser(Guild guild, String id){
        String fName="llGetUser.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsGetUserById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsRetrieveUserById(Guild guild, String id){
        String fName="lsRetrieveUserById.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsRetrieveUserById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUserById(Guild guild, String id){
        String fName="lsGetUserById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            return lsGetUserById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static User lsGetUser(lcGlobalHelper global, String id){
        String fName="llGetUser.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            User user=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                user=lsGetUser(jda, id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsRetrieveUserById(lcGlobalHelper global, String id){
        String fName="lsRetrieveUserById.";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            User user=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                user=lsRetrieveUserById(jda, id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static User lsGetUserById(lcGlobalHelper global, String id){
        String fName="lsGetUserById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            User user=null;
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                user=lsGetUserById(jda, id);
                if(user!=null){
                    return user;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetUserMentionById(lcGlobalHelper global, String id){
        String fName="lsGetMemberMentionById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            String mention="";
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                mention=lsGetUserMentionById(jda, id);
                if(mention!=null&&!mention.isBlank()){
                    return mention;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static String getAuthorIcon(User user){
        String fName = "[getAuthorIcon]";Logger logger = Logger.getLogger(lsChannelHelper.class);
        try{
            return user.getEffectiveAvatarUrl().replaceFirst("gif","png")+"?size=512";
        }
        catch (Exception ex){ logger.error(fName+"exception="+ex); return null;}
    }

    static String lsGetUserMentionById(JDA jda, long id){
        String fName="lsGetMemberMentionById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            User user=lsGetUserById(jda,id);
            if(user!=null){
                return user.getAsMention();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetUserMentionById(JDA jda, String id){
        String fName="lsGetMemberMentionById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            User user=lsGetUserById(jda,id);
            if(user!=null){
                return user.getAsMention();
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetUserMentionById(Guild guild, String id){
        String fName="lsGetMemberMentionById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsGetUserMentionById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetUserMentionById(Guild guild, long id){
        String fName="lsGetMemberMentionById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName);
            return lsGetUserMentionById(guild.getJDA(),id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }
    static String lsGetUserMentionById(lcGlobalHelper global, long id){
        String fName="lsGetMemberMentionById.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            String mention="";
            List<JDA> jdaList=global.getJDAList();
            for(JDA jda:jdaList){
                mention=lsGetUserMentionById(jda, id);
                if(mention!=null&&!mention.isBlank()){
                    return mention;
                }
            }
            return null;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            return null;
        }
    }

    static boolean lsIsUser(User user,User.UserFlag flag){
        String fName="[lsIsUser]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            logger.info(fName+"user="+user.getId()+" ,flag="+flag.getRawValue());
            return lsIsUser(user.getFlags(),flag);
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsUser(EnumSet<User.UserFlag> flags,User.UserFlag flag){
        String fName="[lsIsUser]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=listContainsFlag(flags, flag);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }

    static boolean lsIsStaff(User user){
        String fName="[lsIsStaff]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            EnumSet<User.UserFlag> flags=user.getFlags();
            boolean result=listContainsFlag(flags, User.UserFlag.STAFF);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsSystemUser(User user){
        String fName="[lsIsSystemUser]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=user.isSystem();
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsDiscordCertifiedModerator(User user){
        String fName="[lsIsDiscordCertifiedModerator]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            EnumSet<User.UserFlag> flags=user.getFlags();
            boolean result=listContainsFlag(flags, User.UserFlag.CERTIFIED_MODERATOR);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }

    static boolean lsIsStaff(List<UserFlag_JDA4_2_1_265> flags){
        String fName="[lsIsStaff]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=listContainsFlag(flags, UserFlag_JDA4_2_1_265.STAFF);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsStaff(EnumSet<User.UserFlag> flags){
        String fName="[lsIsStaff]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=listContainsFlag(flags, User.UserFlag.STAFF);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsSystemUser(List<UserFlag_JDA4_2_1_265> flags){
        String fName="[lsIsSystemUser]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=listContainsFlag(flags, UserFlag_JDA4_2_1_265.SYSTEM);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsSystemUser(EnumSet<User.UserFlag> flags){
        String fName="[lsIsSystemUser]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=listContainsFlag(flags, User.UserFlag.SYSTEM);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static boolean lsIsDiscordCertifiedModerator(List<UserFlag_JDA4_2_1_265> flags){
        String fName="[lsIsDiscordCertifiedModerator]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            boolean result=listContainsFlag(flags, UserFlag_JDA4_2_1_265.CERTIFIED_MODERATOR);
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }

    static private boolean listContainsFlag(List<UserFlag_JDA4_2_1_265> flags, UserFlag_JDA4_2_1_265 userFlag){
        String fName="[listContainsFlag]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            if(flags==null){
                logger.warn(fName+"userFlag==null");
                return false;
            }
            if(userFlag==null){
                logger.warn(fName+"userFlag==null");
                return false;
            }
            if(flags.isEmpty()){
                logger.warn(fName+"flags.isEmpty");
                return false;
            }
            boolean result=false;
            logger.info(fName+"flags.size="+flags.size());
            for(UserFlag_JDA4_2_1_265 flag:flags){
                if(flag== userFlag){
                    result=true;
                    break;
                }
            }
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static private boolean listContainsFlag(EnumSet<User.UserFlag> flags, User.UserFlag userFlag){
        String fName="[listContainsFlag]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            if(flags==null){
                logger.warn(fName+"userFlag==null");
                return false;
            }
            if(userFlag==null){
                logger.warn(fName+"userFlag==null");
                return false;
            }
            List<User.UserFlag>listflags=convertEnumSet2LIst(flags);
            if(listflags.isEmpty()){
                logger.warn(fName+"flags.isEmpty");
                return false;
            }
            boolean result=false;
            logger.info(fName+"flags.size="+listflags.size());
            for(User.UserFlag flag:listflags){
                if(flag== userFlag){
                    result=true;
                    break;
                }
            }
            logger.info(fName+"result="+result);
            return result;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return false;
        }
    }
    static private List<User.UserFlag> convertEnumSet2LIst(EnumSet<User.UserFlag> flags){
        String fName="[lsGetUserFlagsAsNameList]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            List<User.UserFlag> list=new ArrayList();
            if(flags==null){
                logger.warn(fName+"flags==null");
                return list;
            }
            if(flags.isEmpty()){
                logger.warn(fName+"flags.isEmpty");
                return list;
            }
            flags.addAll(flags);
            logger.info(fName+"list.size="+list.size());
            return list;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return new ArrayList<>();
        }
    }
    static List<String> lsGetUserFlagsAsNameList(User user){
        String fName="[lsGetUserFlagsAsNameList]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            List<String>list=new ArrayList<>();
            List<UserFlag_JDA4_2_1_265>flags=lsGetUserFlagsAsList(user);
            if(flags.isEmpty()){
                logger.warn(fName+"flags.isEmpty");
                return list;
            }
            logger.info(fName+"flags.size="+flags.size());
            for(UserFlag_JDA4_2_1_265 flag:flags){
                list.add(flag.getName());
            }
            logger.info("list.size="+list.size());
            return list;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return new ArrayList<>();
        }
    }
    static List<UserFlag_JDA4_2_1_265> lsGetUserFlagsAsList(User user){
        String fName="[lsGetUserFlagsAsList]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            List<UserFlag_JDA4_2_1_265>list=new ArrayList<>();
            EnumSet<UserFlag_JDA4_2_1_265>enumSet=lsGetUserFlags(user);
            if(enumSet==null){
                logger.warn(fName+"enumSet==null");
                return list;
            }
            if(enumSet.isEmpty()){
                logger.warn(fName+"enumSet.isEmpty");
                return list;
            }
            list.addAll(enumSet);
            logger.info(fName+"list.size="+list.size());
            return list;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return new ArrayList<>();
        }
    }
    static EnumSet<UserFlag_JDA4_2_1_265> lsGetUserFlags(User user){
        String fName="[lsGetUserFlags]";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            int flagsRaw=user.getFlagsRaw();
            logger.info(fName + "flagsRaw="+flagsRaw);
            EnumSet<UserFlag_JDA4_2_1_265> foundFlags = EnumSet.noneOf(UserFlag_JDA4_2_1_265.class);
            if (flagsRaw == 0) {
                logger.info(fName + "myoutput:none");
                return foundFlags;
            } else {
                UserFlag_JDA4_2_1_265[] var2 = UserFlag_JDA4_2_1_265.values();
                int var3 = var2.length;
                logger.info(fName + "var3="+var3);
                for(int var4 = 0; var4 < var3; ++var4) {
                    UserFlag_JDA4_2_1_265 flag = var2[var4];
                    logger.info(fName + "var4="+var4+", flag:rawvalue="+flag.getRawValue()+", offset="+flag.getOffset()+", name="+flag.getName());
                    if (flag != UserFlag_JDA4_2_1_265.UNKNOWN && (flagsRaw & flag.getRawValue()) == flag.getRawValue()) {
                        logger.info(fName + "adding");
                        foundFlags.add(flag);
                    }
                }
            }
            return foundFlags;
        }
        catch(Exception e){
            logger.error(fName+"exception=" + e);
            return null;
        }
    }
    enum UserFlag_JDA4_2_1_265
    {
        //copied fromn https://github.com/DV8FromTheWorld/JDA/blob/e44bf71324cc58683735d0d2f609bca83b481aa1/src/main/java/net/dv8tion/jda/api/entities/User.java
        STAFF(               0, "Discord Employee"),
        PARTNER(             1, "Partnered Server Owner"),
        HYPESQUAD(           2, "HypeSquad Events"),
        BUG_HUNTER_LEVEL_1(  3, "Bug Hunter Level 1"),

        // HypeSquad
        HYPESQUAD_BRAVERY(   6, "HypeSquad Bravery"),
        HYPESQUAD_BRILLIANCE(7, "HypeSquad Brilliance"),
        HYPESQUAD_BALANCE(   8, "HypeSquad Balance"),

        EARLY_SUPPORTER(     9, "Early Supporter"),
        TEAM_USER(          10, "Team User"),
        @Deprecated
        @ForRemoval
        @ReplaceWith("User.isSystem()")
        @DeprecatedSince("4.3.0")
        SYSTEM(             12, "System User"),
        BUG_HUNTER_LEVEL_2( 14, "Bug Hunter Level 2"),
        VERIFIED_BOT(       16, "Verified Bot"),
        VERIFIED_DEVELOPER( 17, "Early Verified Bot Developer"),
        CERTIFIED_MODERATOR(18, "Discord Certified Moderator"),

        UNKNOWN(-1, "Unknown");

        /**
         * Empty array of UserFlag enum, useful for optimized use in {@link java.util.Collection#toArray(Object[])}.
         */
        public static final UserFlag_JDA4_2_1_265[] EMPTY_FLAGS = new UserFlag_JDA4_2_1_265[0];

        private final int offset;
        private final int raw;
        private final String name;

        UserFlag_JDA4_2_1_265(int offset, @Nonnull String name)
        {
            this.offset = offset;
            this.raw = 1 << offset;
            this.name = name;
        }

        /**
         * The readable name as used in the Discord Client.
         *
         * @return The readable name of this UserFlag.
         */
        @Nonnull
        public String getName()
        {
            return this.name;
        }

        /**
         * The binary offset of the flag.
         *
         * @return The offset that represents this UserFlag.
         */
        public int getOffset()
        {
            return offset;
        }

        /**
         * The value of this flag when viewed as raw value.
         * <br>This is equivalent to: <code>1 {@literal <<} {@link #getOffset()}</code>
         *
         * @return The raw value of this specific flag.
         */
        public int getRawValue()
        {
            return raw;
        }

        /**
         * Gets the first UserFlag relating to the provided offset.
         * <br>If there is no UserFlag that matches the provided offset,
         * {@link #UNKNOWN} is returned.
         *
         * @param  offset
         *         The offset to match a UserFlag to.
         *
         * @return UserFlag relating to the provided offset.
         */
        @Nonnull
        public static lsUserHelper.UserFlag_JDA4_2_1_265 getFromOffset(int offset)
        {
            for (UserFlag_JDA4_2_1_265 flag : values())
            {
                if (flag.offset == offset)
                    return flag;
            }
            return UNKNOWN;
        }

        /**
         * A set of all UserFlags that are specified by this raw int representation of
         * flags.
         *
         * @param  flags
         *         The raw {@code int} representation if flags.
         *
         * @return Possibly-empty EnumSet of UserFlags.
         */
        @Nonnull
        public static EnumSet<UserFlag_JDA4_2_1_265> getFlags(int flags)
        {
            final EnumSet<UserFlag_JDA4_2_1_265> foundFlags = EnumSet.noneOf(UserFlag_JDA4_2_1_265.class);

            if (flags == 0)
                return foundFlags; //empty

            for (UserFlag_JDA4_2_1_265 flag : values())
            {
                if (flag != UNKNOWN && (flags & flag.raw) == flag.raw)
                    foundFlags.add(flag);
            }

            return foundFlags;
        }

        /**
         * This is effectively the opposite of {@link #getFlags(int)}, this takes 1 or more UserFlags
         * and returns the bitmask representation of the flags.
         *
         * @param  flags
         *         The array of flags of which to form into the raw int representation.
         *
         * @throws java.lang.IllegalArgumentException
         *         When the provided UserFlags are null.
         *
         * @return bitmask representing the provided flags.
         */
        public static int getRaw(@Nonnull UserFlag_JDA4_2_1_265... flags){
            Checks.noneNull(flags, "UserFlags");

            int raw = 0;
            for (UserFlag_JDA4_2_1_265 flag : flags)
            {
                if (flag != null && flag != UNKNOWN)
                    raw |= flag.raw;
            }

            return raw;
        }

        /**
         * This is effectively the opposite of {@link #getFlags(int)}. This takes a collection of UserFlags
         * and returns the bitmask representation of the flags.
         * <br>Example: {@code getRaw(EnumSet.of(UserFlag.STAFF, UserFlag.HYPESQUAD))}
         *
         * @param  flags
         *         The flags to convert
         *
         * @throws java.lang.IllegalArgumentException
         *         When the provided UserFLags are null.
         *
         * @return bitmask representing the provided flags.
         *
         * @see java.util.EnumSet EnumSet
         */
        public static int getRaw(@Nonnull Collection<UserFlag_JDA4_2_1_265> flags)
        {
            Checks.notNull(flags, "Flag Collection");

            return getRaw(flags.toArray(EMPTY_FLAGS));
        }
    }
    static boolean lsUserIsBotOwner(User user){
        String fName="lsUserIsBotOwner.";Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            switch (user.getId()){
                case "345883700124319744":
                case "805020083201114132":
                    logger.info(fName+"user that should always return false");
                    return false;
            }
            ApplicationInfo applicationInfo=user.getJDA().retrieveApplicationInfo().complete();
            boolean result=applicationInfo.getOwner().getIdLong()==user.getIdLong();
            logger.info(fName+".result="+result);
            return  result;
        }
        catch(Exception e){
            logger.error(fName+".exception=" + e);
            return false;
        }
    }

    static String userUrl(String user_id){
        String fName="[userUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return lsDiscordApi.liGetUserUrl.replaceAll("!USER",user_id);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userUrl(long user_id){
        String fName="[userUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return userUrl(String.valueOf(user_id));
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userUrl(User user){
        String fName="[userUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return userUrl(user.getId());
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userUrl(Member member){
        String fName="[userUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return userUrl(member.getId());
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userAvatarUrl(String user_id,String avatar_id,int size){
        String fName="[userAvatarUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            String str=lsDiscordApi.liUserAvatarUrl.replaceAll("!USER",user_id).replaceAll("!AVATAR",avatar_id);
            if(size>0){
                str+="size="+size;
            }
            if(lsDiscordApi.isIdAnimated(avatar_id)){
                str=str.replaceAll("!EXT","gif");
            }else{
                str=str.replaceAll("!EXT","png");
            }
            return str;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userBannerUrl(String user_id,String avatar_id,int size){
        String fName="[userBannerUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            String str=lsDiscordApi.liUserBannerUrl.replaceAll("!USER",user_id).replaceAll("!BANNER",avatar_id);
            if(size>0){
                str+="size="+size;
            }
            if(lsDiscordApi.isIdAnimated(avatar_id)){
                str=str.replaceAll("!EXT","gif");
            }else{
                str=str.replaceAll("!EXT","png");
            }
            return str;
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userAvatarUrl(String user_id,String avatar_id){
    String fName="[userAvatarUrl]";
    Logger logger = Logger.getLogger(lsUserHelper.class);
    try{
        return userAvatarUrl(user_id,avatar_id,0);
    }
    catch(Exception e){
        logger.error(".exception=" + e);
        logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        return null;
    }
}
    static String userBannerUrl(String user_id,String avatar_id){
        String fName="[userBannerUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
           return userBannerUrl(user_id,avatar_id,0);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userAvatarUrl(User user,int size){
        String fName="[userAvatarUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return userAvatarUrl(user.getId(),user.getAvatarId(),size);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static String userAvatarUrl(User user){
        String fName="[userAvatarUrl]";
        Logger logger = Logger.getLogger(lsUserHelper.class);
        try{
            return userAvatarUrl(user.getId(),user.getAvatarId());
        }
        catch(Exception e){
            logger.error(".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }



}