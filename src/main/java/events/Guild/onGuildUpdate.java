package events.Guild;

import net.dv8tion.jda.api.events.guild.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;

public class onGuildUpdate extends ListenerAdapter {
	Logger logger = Logger.getLogger(getClass());
    String cName="[onGuildUpdate]";
    public onGuildUpdate(){
        logger.warn(".constructor");
    }

    public void	onGuildUpdateAfkChannel(@Nonnull GuildUpdateAfkChannelEvent event){
        String fName="onGuildUpdateAfkChannel";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldAfkChannel().getId()+" newValue="+event.getNewAfkChannel());
    }
    public void	onGuildUpdateAfkTimeout(@Nonnull GuildUpdateAfkTimeoutEvent event){
        String fName="onGuildUpdateAfkTimeout";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldAfkTimeout().getSeconds()+" newValue="+event.getOldAfkTimeout().getSeconds());
    }
    public void	onGuildUpdateBanner(@Nonnull GuildUpdateBannerEvent event){
        String fName="onGuildUpdateBanner";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldBannerUrl()+" newValue="+event.getNewBannerUrl());
    }
    public void	onGuildUpdateBoostCount(@Nonnull GuildUpdateBoostCountEvent event){
        String fName="onGuildUpdateBoostCount";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldBoostCount()+" newValue="+event.getNewBoostCount());
    }
    public void	onGuildUpdateBoostTier(@Nonnull GuildUpdateBoostTierEvent event){
        String fName="onGuildUpdateBoostTier";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldBoostTier().getKey()+" newValue="+event.getNewBoostTier().getKey());
    }
    public void	onGuildUpdateDescription(@Nonnull GuildUpdateDescriptionEvent event){
        String fName="onGuildUpdateDescription";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldDescription()+" newValue="+event.getNewDescription());
    }
    public void	onGuildUpdateExplicitContentLevel(@Nonnull GuildUpdateExplicitContentLevelEvent event){
        String fName="onGuildUpdateExplicitContentLevel";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldLevel().getKey()+" newValue="+event.getNewLevel().getKey());
    }
    public void	onGuildUpdateFeatures(@Nonnull GuildUpdateFeaturesEvent event){
        String fName="onGuildUpdateFeatures";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldFeatures()+" newValue="+event.getNewFeatures());
    }
    public void	onGuildUpdateIcon(@Nonnull GuildUpdateIconEvent event){
        String fName="onGuildUpdateIcon";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldIconUrl()+" newValue="+event.getNewIconUrl());
    }
    public void	onGuildUpdateMaxMembers(@Nonnull GuildUpdateMaxMembersEvent event){
        String fName="onGuildUpdateMaxMembers";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldMaxMembers()+" newValue="+event.getNewMaxMembers());
    }
    public void	onGuildUpdateMaxPresences(@Nonnull GuildUpdateMaxPresencesEvent event){
        String fName="onGuildUpdateMaxPresences";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldMaxPresences()+" newValue="+event.getNewMaxPresences());
    }
    public void	onGuildUpdateMFALevel(@Nonnull GuildUpdateMFALevelEvent event){
        String fName="onGuildUpdateMFALevel";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldMFALevel().getKey()+" newValue="+event.getNewMFALevel().getKey());
    }
    public void	onGuildUpdateName(@Nonnull GuildUpdateNameEvent event){
        String fName="onGuildUpdateName";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldName()+" newValue="+event.getNewName());
    }
    public void	onGuildUpdateNotificationLevel(@Nonnull GuildUpdateNotificationLevelEvent event){
        String fName="onGuildUpdateNotificationLevel";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldNotificationLevel().getKey()+" newValue="+event.getNewNotificationLevel().getKey());
    }
    public void	onGuildUpdateOwner(@Nonnull GuildUpdateOwnerEvent event){
        String fName="onGuildUpdateOwner";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldOwnerId()+" newValue="+event.getNewOwnerId());
    }
    public void	onGuildUpdateSplash(@Nonnull GuildUpdateSplashEvent event){
        String fName="onGuildUpdateSplash";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldSplashUrl()+" newValue="+event.getNewSplashUrl());
    }
    public void	onGuildUpdateSystemChannel(@Nonnull GuildUpdateSystemChannelEvent event){
        String fName="onGuildUpdateSystemChannel";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldSystemChannel().getId()+" newValue="+event.getNewSystemChannel().getId());
    }
    public void	onGuildUpdateVanityCode(@Nonnull GuildUpdateVanityCodeEvent event){
        String fName="onGuildUpdateVanityCode";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldVanityUrl()+" newValue="+event.getNewVanityUrl());
    }
    public void	onGuildUpdateVerificationLevel(@Nonnull GuildUpdateVerificationLevelEvent event){
        String fName="onGuildUpdateVerificationLevel";
        logger.info(fName+" guild="+event.getGuild().getId()+" oldValue="+event.getOldVerificationLevel().getKey()+" newValue="+event.getNewVerificationLevel().getKey());
    }

}
