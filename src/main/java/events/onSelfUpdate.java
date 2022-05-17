package events;

import net.dv8tion.jda.api.events.self.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onSelfUpdate extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    String cName="[onSelfUpdate]";
    public onSelfUpdate(){
         logger.warn(cName+".constructor");
    }
    public void	onSelfUpdateAvatar(@Nonnull SelfUpdateAvatarEvent event){
        String fName="onSelfUpdateAvatar";
       logger.info(cName+fName+" old="+event.getOldAvatarUrl()+" new="+event.getNewAvatarUrl());
    }
    public void	onSelfUpdateMFA(@Nonnull SelfUpdateMFAEvent event){
        String fName="onSelfUpdateMFA";
        logger.info(cName+fName+" old="+event.getOldValue()+" new="+event.getNewValue());
    }
    public void	onSelfUpdateName(@Nonnull SelfUpdateNameEvent event){
        String fName="onSelfUpdateName";
        logger.info(cName+fName+" old="+event.getOldName()+" new="+event.getNewName());
    }
    public void	onSelfUpdateDiscriminator(@Nonnull SelfUpdateDiscriminatorEvent event){
        String fName="onSelfUpdateDiscriminator";
        logger.info(cName+fName+" old="+event.getOldDiscriminator()+" new="+event.getNewDiscriminator());
    }
    public void	onSelfUpdateVerified(@Nonnull SelfUpdateVerifiedEvent event){
        String fName="onSelfUpdateVerified";
        logger.info(cName+fName+" old="+event.getOldValue()+" new="+event.getNewValue());
    }
}
