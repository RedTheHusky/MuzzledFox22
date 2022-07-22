package restraints;

import models.lc.json.profile.lcJSONGuildProfile;
import models.lc.json.profile.lcJSONUserProfile;
import models.lcGlobalHelper;
import models.ls.lsMemberHelper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import org.apache.log4j.Logger;
import restraints.in.iRestraints;

import java.util.Arrays;
//NOT USED
public class nodeUserRestraints implements iRestraints {
    Logger logger = Logger.getLogger(getClass()); String cName="[nodeUserRestraints]";
    lcJSONUserProfile gUserProfile;
    Member gMember; User gUser; Guild gGuild;
    class gag{
        boolean on=false;
        public Boolean on(){
            String fName="[on]";logger.info(fName);
            try {
                return on;
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return  false;
            }
        }
    }
    public Boolean getProfile(lcGlobalHelper global, Guild guild, Member member , lcJSONGuildProfile gProfileRestrains){
        String fName="[getProfile]";
        try {
            logger.info(fName);
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            gMember=member;gUser=member.getUser();gGuild=guild;
            gUserProfile=global.getUserProfile(profileName,member,guild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(global,member,guild);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            gUserProfile= iUserInit(gUserProfile,gProfileRestrains);
            global.putUserProfile(gUserProfile,profileName);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }

    }
    public Boolean getProfile(lcGlobalHelper global, Guild guild, User user , lcJSONGuildProfile gProfileRestrains){
        String fName="[getProfile]";
        try {
            logger.info(fName);
            logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
            gUser=user;gGuild=guild;
            gMember=lsMemberHelper.lsGetMember(guild,user);
            gUserProfile=global.getUserProfile(profileName,user,guild);
            if(gUserProfile!=null&&gUserProfile.isProfile()){
                logger.info(fName + ".is locally cached");
            }else{
                logger.info(fName + ".need to get or create");
                gUserProfile=new lcJSONUserProfile(global,user,guild);
                if(gUserProfile.getProfile(table)){
                    logger.info(fName + ".has sql entry");
                }
            }
            gUserProfile= iUserInit(gUserProfile,gProfileRestrains);
            global.putUserProfile(gUserProfile,profileName);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }

    }

    public Boolean getProfileNoCatch(lcGlobalHelper global, Guild guild, Member member , lcJSONGuildProfile gProfileRestrains){
        String fName="[getProfileNoCatch]";
        try {
            logger.info(fName);
            logger.info(fName + ".user:"+ member.getId()+"|"+member.getUser().getName());
            gMember=member;gUser=member.getUser();gGuild=guild;
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(global,member,guild);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");
            }
            gUserProfile= iUserInit(gUserProfile,gProfileRestrains);
            if(!gUserProfile.isUpdated){
                logger.info(fName + ".no update>ignore");return true;
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }

        return true;
    }
    public Boolean getProfileNoCatch(lcGlobalHelper global, Guild guild, User user , lcJSONGuildProfile gProfileRestrains){
        String fName="[getProfileNoCatch]";
        try {
            logger.info(fName);
            logger.info(fName + ".user:"+ user.getId()+"|"+user.getName());
            gUser=user;gGuild=guild;
            gMember=lsMemberHelper.lsGetMember(guild,user);
            logger.info(fName + ".need to get or create");
            gUserProfile=new lcJSONUserProfile(global,user,guild);
            if(gUserProfile.getProfile(table)){
                logger.info(fName + ".has sql entry");
            }
            gUserProfile= iUserInit(gUserProfile,gProfileRestrains);
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public Boolean getValues4Profile(){
        String fName="[getValues4Profile]";
        try {
            logger.info(fName);

            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
}
