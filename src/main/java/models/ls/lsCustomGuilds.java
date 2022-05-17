package models.ls;


import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface lsCustomGuilds {
    //BDSM
    String lsGuildKeyBdsm="475589373791043594";
    String lsGuildBdsmMemberKey="475668594647236609";String lsGuildBdsmNewMemberKey="482088827167571970"; String lsGuildBdsmStaffKey="476024097155842079";
    String lsGuildBDSMRole_BDSMInfo="745552516451794984", lsGuildBDSMRole_BasicInfo="738296643798958120",lsGuildBDSMRole_KinkInfo="745550137295634483", lsGuildBDSMRole_OtherInfo="745553382412124232";
    String lsGuildBDSMRole_BDSM="475663002096500757",lsGuildBDSMRole_BDSMVerified="767952526913044552";
    String lsGuildBDSMRole_ABDL="476314106417250304",lsGuildBDSMRole_ABDLVerified="767952599613177876";
    String lsGuildBDSMRole_Watersports="475607995137392650",lsGuildBDSMRole_WatersportsVerified="767952643209166850";
    String lsGuildBDSMRole_Latex="475731617063895040",lsGuildBDSMRole_LatexVerified="767956153203163157";
    String lsGuildBDSMRole_ToManyKinks="536751857947639823",lsGuildBDSMROle_Verified="476764066250096641";
    String lsGuildBdsmInviteLink="https://discord.gg/kS89myS";
    //CHASTITY
    String lsGuildKeyChastity="486465588235862034";
    String lsGuildChastityMemberKey="486474256176119808";String lsGuildChastityNewMemberKey="486474280742289418"; String lsGuildChastityStaffKey="486472909179846667";
    String lsGuildChastityInviteLink="https://discord.gg/x4shBAn";
    //ASYLUM
    String lsGuildKeyAsylum="482743226848116757";
    String lsGuildAsylumMemberKey="482750587533000705";String lsGuildAsylumNewMemberKey="482750547858948096"; String lsGuildAsylumStaffKey="482747266294349824";String lsGuildAsylum18PlusKey="482853594861666304";
    String lsGuildAsylumInviteLink="https://discord.gg/JJA3pWR";
    //PRISON
    String lsGuildKeyPrison="482933003056840714";
    String lsGuildPrisonMemberKey="482938692634411018";String lsGuildPrisonNewMemberKey="482938715996684328"; String lsGuildPrisonStaffKey="482938452896120842";String lsGuildPrison18PlusKey="482938793347907595";
    String lsGuildPrisonInviteLink="https://discord.gg/abwff3C";
    //SNUFF
    String lsGuildKeySnuff="486531354826375179";
    String lsGuildSnuffMemberKey="489835572492238858";String lsGuildSnuffNewMemberKey="489835520352976906"; String lsGuildSnuffStaffKey="489821299628179477";
    String lsGuildSnuffInviteLink="https://discord.gg/5e9WeCm";
    //BLACK GAZZA
    String lsGuildKeyBlackGazza="345772350819794947";
    //Administration
    String lsGuildKeyAdministration="481700616457027597";
    //Lax
    String lsGuildKeyLax ="670411732581089308";
    static boolean lsIsCustomGuild(Guild guild){
        String fName="[lsIsCustomGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyBdsm)||id.equals(lsGuildKeyChastity)||id.equals(lsGuildKeySnuff)||id.equals(lsGuildKeyAsylum)||id.equals(lsGuildKeyPrison)||id.equals(lsGuildKeyAdministration)){
                logger.info(fName+".is part of Red's guild");    return true;
            }
            if(id.equals(lsGuildKeyLax)){
                logger.info(fName+".is part of Lax's guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean lsIsCustomAdultGuild(Guild guild){
        String fName="[lsIsCustomAdultGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyBdsm)||id.equals(lsGuildKeyChastity)||id.equals(lsGuildKeySnuff)){
                logger.info(fName+".is part of Red's adult guild");    return true;
            }
            if(id.equals(lsGuildKeyLax)){
                logger.info(fName+".is part of Lax's adult guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean lsIsUIEditGuild(Guild guild){
        String fName="[lsIsUIEditGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyBdsm)||id.equals(lsGuildKeyChastity)||id.equals(lsGuildKeySnuff)||id.equals(lsGuildKeyAsylum)||id.equals(lsGuildKeyPrison)||id.equals(lsGuildKeyAdministration)){
                logger.info(fName+".is part of Red's guild");    return true;
            }
            if(id.equals(lsGuildKeyLax)){
                logger.info(fName+".is part of Lax's guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean lsIsCustomGuild4Red(Guild guild){
        String fName="[lsIsCustomGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyBdsm)||id.equals(lsGuildKeyChastity)||id.equals(lsGuildKeySnuff)||id.equals(lsGuildKeyAsylum)||id.equals(lsGuildKeyPrison)||id.equals(lsGuildKeyAdministration)){
                logger.info(fName+".is part of Red's guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean lsIsCustomGuild4Lax(Guild guild){
        String fName="[lsIsCustomGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyLax)){
                logger.info(fName+".is part of Lax's guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean lsIsCustomAdultGuild4Red(Guild guild){
        String fName="[lsIsCustomAdultGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyBdsm)||id.equals(lsGuildKeyChastity)||id.equals(lsGuildKeySnuff)){
                logger.info(fName+".is part of Red's adult guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    static boolean lsIsCustomAdultGuild4Lax(Guild guild){
        String fName="[lsIsCustomAdultGuild]";
        Logger logger = Logger.getLogger(lsCustomGuilds.class);
        try{
            String id=guild.getId();
            logger.info(fName+".id=" + id);
            if(id.equals(lsGuildKeyLax)){
                logger.info(fName+".is part of Lax's adult guild");    return true;
            }
            logger.info(fName+".not custom guild");    return false;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
}
