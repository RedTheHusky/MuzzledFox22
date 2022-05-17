package models.ls;

import models.ll.colors.llColors;
import models.ll.llMessageHelper;
import models.llGlobalHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.apache.log4j.Logger;

import java.awt.*;

public interface lsQuickMessages extends llColors
{
    static String lsSplitMessageFilter=" ";//filter="[, ?.@]+";
    static void lsSendDeny_RequirePermission_MANAGESERVER(User author,String title){
        String fName="lsSendDeny_RequirePermission_MANAGESERVER";
        Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(author,title, "Require permission Manage Server from author!", llColorRed);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
        }
    }
    static void lsSendDeny_RequirePermission_BAN(User author,String title){
        String fName="lsSendDeny_RequirePermission_BAN";
        Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(author,title, "Require permission BAN MEMBERS from author!", llColorRed);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
        }
    }
    static void lsSendDeny_RequirePermission_VIEWAUDITLOGS(User author,String title){
        String fName="lsSendDeny_RequirePermission_VIEWAUDITLOGS";
        Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(author,title, "Require permission VIEW AUDITLOGS from author!", llColorRed);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
        }
    }
    static void lsSendDeny_RequireStaff(User author,String title){
        String fName="lsSendDeny_RequireStaff";
        Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(author,title, "Staff only allowed to use this command!", llColorRed);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
        }
    }
    static void lsSendDeny_RequireBot(User author,String title){
        String fName="lsSendDeny_RequireBot";
        Logger logger = Logger.getLogger(fName);
        try{
            lsMessageHelper.lsSendQuickEmbedMessage(author,title, "Bot owner only allowed to use this command!", llColorRed);
        }
        catch(Exception e){
            logger.error(".exception=" + e);
        }
    }
}