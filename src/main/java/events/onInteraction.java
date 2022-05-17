package events;

import interaction.button.buttonControler;
import interaction.slash.slashControler;
import models.lc.interaction.messagecomponent.lcMessageBuildComponent;
import models.lcGlobalHelper;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.application.ApplicationCommandCreateEvent;
import net.dv8tion.jda.api.events.application.ApplicationCommandDeleteEvent;
import net.dv8tion.jda.api.events.application.ApplicationCommandUpdateEvent;
import net.dv8tion.jda.api.events.application.GenericApplicationCommandEvent;
import net.dv8tion.jda.api.events.interaction.*;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class onInteraction extends ListenerAdapter {
    public boolean debugInfo=false;
	Logger logger = Logger.getLogger(getClass());
    String cName="[onRole]";
    public onInteraction(){
        String fName="interaction";
       logger.warn("@"+fName+".listenerLoaded");
    }
    lcGlobalHelper global;
    public onInteraction(lcGlobalHelper g){
        String fName="interaction";
        logger.warn("@"+fName+".listenerLoaded");
        global=g;
    }
    public void onGenericApplicationCommand(@Nonnull GenericApplicationCommandEvent event){
        String fName="onApplicationCommandCreate";
        logger.info(fName);
        Command command=event.getCommand();
        Guild guild=event.getGuild();
        if(guild==null){
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName());
        }else{
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName()+", guild_id="+guild.getId());
        }
    }
    public void onApplicationCommandCreate(@Nonnull ApplicationCommandCreateEvent event){
        String fName="onApplicationCommandCreate";
        logger.info(fName);
        Command command=event.getCommand();
        Guild guild=event.getGuild();
        if(guild==null){
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName());
        }else{
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName()+", guild_id="+guild.getId());
        }
    }
    public void onApplicationCommandUpdate(@Nonnull ApplicationCommandUpdateEvent event){
        String fName="onApplicationCommandUpdate";
        logger.info(fName);
        Command command=event.getCommand();
        Guild guild=event.getGuild();
        if(guild==null){
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName());
        }else{
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName()+", guild_id="+guild.getId());
        }
    }
    public void onApplicationCommandDelete(@Nonnull ApplicationCommandDeleteEvent event){
        String fName="onApplicationCommandDelete";
        logger.info(fName);
        Command command=event.getCommand();
        Guild guild=event.getGuild();
        if(guild==null){
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName());
        }else{
            logger.info(fName+"application_id"+command.getApplicationId()+",application_name="+command.getName()+", guild_id="+guild.getId());
        }
    }
    public void onCommandInteraction(@Nonnull CommandInteraction event){
        String fName="[onCommandInteraction]";
        logger.info(fName);
        String id=event.getId();
        String name=event.getName();
        MessageChannel messageChannel=event.getChannel();
        Guild guild=event.getGuild();
        Member member=event.getMember();
        StringBuilder str=new StringBuilder();
        TextChannel textChannel=event.getTextChannel();
        PrivateChannel privateChannel =event.getPrivateChannel();
        if(id!=null){
            str.append("id="+id);
        }
        if(name!=null){
            str.append(", name="+name);
        }
        if(member!=null){
            str.append(", member="+member.getId());
        }
        if(guild!=null){
            str.append(", guild="+guild.getId());
        }
        if(messageChannel!=null){
            str.append(", messageChannel_id="+messageChannel.getId());
        }
        if(textChannel!=null){
            str.append(", textChannel_id="+textChannel.getId());
        }
        if(privateChannel!=null){
            str.append(", privateChannel_id="+privateChannel.getId());
        }

    }
    public void onSlashCommand(@Nonnull SlashCommandEvent event){
        String fName="[onSlashCommandEvent]";
        logger.info(fName);
        try {
            String id=event.getId();
            String name=event.getName();
            MessageChannel messageChannel=event.getChannel();

            Member member=event.getMember();
            StringBuilder str=new StringBuilder();
            if(id!=null){
                str.append("id="+id);
            }
            if(name!=null){
                str.append(", name="+name);
            }
            if(member!=null){
                str.append(", member="+member.getId());
            }

            if(messageChannel!=null){
                str.append(", messageChannel_id="+messageChannel.getId());
            }
            try {
                if(event.isFromGuild()){
                    Guild guild=event.getGuild();
                    TextChannel textChannel=event.getTextChannel();
                    if(guild!=null){
                        str.append(", guild="+guild.getId());
                    }
                    if(textChannel!=null){
                        str.append(", textChannel_id="+textChannel.getId());
                    }
                }else{
                    PrivateChannel privateChannel =event.getPrivateChannel();
                    if(privateChannel!=null){
                        str.append(", privateChannel_id="+privateChannel.getId());
                    }
                }

            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName + "="+str.toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        new slashControler(global,event);
    }
    public void onButtonClick(@Nonnull ButtonClickEvent event){
        String fName="[onButtonClickEvent]";
        logger.info(fName);
        try {
            String id=event.getId();
            String componentId=event.getComponentId();
            MessageChannel messageChannel=event.getChannel();
            Guild guild=event.getGuild();
            Member member=event.getMember();
            StringBuilder str=new StringBuilder();
            TextChannel textChannel=null;
            PrivateChannel privateChannel =null;
            switch (event.getChannelType()){
                case PRIVATE:privateChannel =event.getPrivateChannel();break;
                case TEXT: textChannel=event.getTextChannel();break;
            }
            if(id!=null){
                str.append("id="+id);
            }
            if(componentId!=null){
                str.append(", componentId="+componentId);
            }
            if(member!=null){
                str.append(", member="+member.getId());
            }
            if(guild!=null){
                str.append(", guild="+guild.getId());
            }
            if(messageChannel!=null){
                str.append(", messageChannel_id="+messageChannel.getId());
            }
            if(textChannel!=null){
                str.append(", textChannel_id="+textChannel.getId());
            }
            if(privateChannel!=null){
                str.append(", privateChannel_id="+privateChannel.getId());
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
        new buttonControler(global,event);
    }
    public void onSelectionMenu(@Nonnull SelectionMenuEvent event){
        String fName="[onSelectionMenu]";
        logger.info(fName);
        try {
            String id=event.getId();
            MessageChannel messageChannel=event.getChannel();

            Member member=event.getMember();
            StringBuilder str=new StringBuilder();
            if(id!=null){
                str.append("id="+id);
            }
            if(member!=null){
                str.append(", member="+member.getId());
            }

            if(messageChannel!=null){
                str.append(", messageChannel_id="+messageChannel.getId());
            }
            try {
                switch (event.getChannelType()){
                    case TEXT:
                        Guild guild=event.getGuild();
                        TextChannel textChannel=event.getTextChannel();
                        if(guild!=null){
                            str.append(", guild="+guild.getId());
                        }
                        if(textChannel!=null){
                            str.append(", textChannel_id="+textChannel.getId());
                        }
                        break;
                    case PRIVATE:
                        PrivateChannel privateChannel =event.getPrivateChannel();
                        if(privateChannel!=null){
                            str.append(", privateChannel_id="+privateChannel.getId());
                        }
                        break;
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName + "="+str.toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    public void onGenericInteractionCreate(@Nonnull GenericInteractionCreateEvent event){
        String fName="[onGenericInteractionCreate]";
        logger.info(fName);
        try {
            StringBuilder str=new StringBuilder();

            int typeraw=event.getTypeRaw();
            String id=event.getId();
            str.append("typeraw="+typeraw);
            str.append(", id="+id);
            try {
                User user=event.getUser();
                if(user!=null){
                    str.append(", user="+user.getId());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                Member member=event.getMember();
                if(member!=null){
                    str.append(", member="+member.getId());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                AbstractChannel messageChannel=event.getChannel();
                if(messageChannel!=null){
                    str.append(", messageChannel_id="+messageChannel.getId());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                InteractionHook hook=event.getHook();
                if(hook!=null){
                    str.append(", hook_token="+hook.getInteraction().getToken());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName + "="+str.toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }
    public void onGenericComponentInteractionCreate(@Nonnull GenericComponentInteractionCreateEvent event){
        String fName="[onGenericInteractionCreate]";
        logger.info(fName);
        try {
            StringBuilder str=new StringBuilder();

            int typeraw=event.getTypeRaw();
            String id=event.getId();
            str.append("typeraw="+typeraw);
            str.append(", id="+id);
            try {
                User user=event.getUser();
                if(user!=null){
                    str.append(", user="+user.getId());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                Member member=event.getMember();
                if(member!=null){
                    str.append(", member="+member.getId());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                AbstractChannel messageChannel=event.getChannel();
                if(messageChannel!=null){
                    str.append(", messageChannel_id="+messageChannel.getId());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            try {
                InteractionHook hook=event.getHook();
                if(hook!=null){
                    str.append(", hook_token="+hook.getInteraction().getToken());
                }
            }catch (Exception e){
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            }
            logger.info(fName + "="+str.toString());
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }

    }

}
