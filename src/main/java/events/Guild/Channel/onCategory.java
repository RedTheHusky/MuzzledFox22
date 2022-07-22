package events.Guild.Channel;

import models.lcGlobalHelper;
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent;
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent;
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;
import workers.SqlLog;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onCategory extends ListenerAdapter {
    lcGlobalHelper gGlobal;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onCategory]";
    public  onCategory(lcGlobalHelper global){
       logger.warn(".constructor");
        gGlobal=global;
    }

    public  void	onCategoryCreate(@Nonnull CategoryCreateEvent event){
        String fName="onCategoryCreate";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getCategory().getId());
        try {
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void	onCategoryDelete(@Nonnull CategoryDeleteEvent event){
        String fName="onCategoryDelete";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getCategory().getId());
        try {
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    public  void	onCategoryUpdateName(@Nonnull CategoryUpdateNameEvent event){
        String fName="onCategoryUpdateName";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getCategory().getId()+ "old="+event.getOldName()+" new="+event.getNewName());
        try {
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }

    /*public  void	onCategoryUpdatePermissions(@Nonnull CategoryUpdatePermissionsEvent event){
        String fName="onCategoryUpdatePermissions";
        logger.info(cName+fName);
        //ReplaceWith("onPermissionOverrideUpdate(), onPermissionOverrideCreate(), and onPermissionOverrideDelete()")
    }*/
    /*
    public  void	onCategoryUpdatePosition(@Nonnull CategoryUpdatePositionEvent event){
        String fName="onCategoryUpdatePosition";
        logger.info(fName+" guild="+event.getGuild().getId()+" channel="+event.getCategory().getId()+ "old="+event.getOldPosition()+" new="+event.getNewPosition());
        try {
            new SqlLog(gGlobal,event);
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }
    */
}
