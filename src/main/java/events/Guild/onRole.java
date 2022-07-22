package events.Guild;

import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onRole extends ListenerAdapter {
    public boolean debugInfo=false;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onRole]";
    public onRole(){
        String fName="eventListener=Role";
       logger.warn("@"+fName+".listenerLoaded");
    }

    public void	onRoleCreate(@Nonnull RoleCreateEvent event){
        String fName="onRoleCreate";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId());
    }
    public void	onRoleDelete(@Nonnull RoleDeleteEvent event){
        String fName="onRoleDelete";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId());
    }
    public void	onRoleUpdateColor(@Nonnull RoleUpdateColorEvent event){
        String fName="onRoleUpdateColor";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId()+" old="+event.getOldValue()+" new="+event.getNewValue());
    }
    public void	onRoleUpdateHoisted(@Nonnull RoleUpdateHoistedEvent event){
        String fName="onRoleUpdateHoisted";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId()+" old="+event.getOldValue()+" new="+event.getNewValue());
    }
    public void	onRoleUpdateMentionable(@Nonnull RoleUpdateMentionableEvent event){
        String fName="onRoleUpdateMentionable";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId()+" old="+event.getOldValue()+" new="+event.getNewValue());
    }
    public void	onRoleUpdateName(@Nonnull RoleUpdateNameEvent event){
        String fName="onRoleUpdateName";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId()+" old="+event.getOldName()+" new="+event.getNewName());
    }
    public void	onRoleUpdatePermissions(@Nonnull RoleUpdatePermissionsEvent event){
        String fName="onRoleUpdatePermissions";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId());
    }
    public void	onRoleUpdatePosition(@Nonnull RoleUpdatePositionEvent event){
        String fName="onRoleUpdatePosition";
        logger.info(fName+" guild="+event.getGuild().getId()+" role="+event.getRole().getId()+" OldPosition="+event.getOldPosition()+" NewPosition="+event.getNewPosition());
    }

}
