package models.ll;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public interface llChannelPermissionOverride {
    default boolean llSetMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission, int mode){
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
                if(result==null){
                    return false;
                }
                if(mode==-1){
                    result.getManager().deny(permission).complete();
                }else
                if(mode==0){
                    result.getManager().clear(permission).complete();
                }else
                if(mode==1){
                    result.getManager().grant(permission).complete();
                }else{
                    return false;
                }
                if(result==null){
                    ;return false;
                }
                return true;
            }else{

                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    default boolean llDenyMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission){
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
              return false;
            }
            if(result.getDenied().contains(permission)){
                return false;
            }
            result.getManager().deny(permission).complete();
            if(result==null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llClearMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission){
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }

            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().clear(permission).complete();
            if(result==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llGrantMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission){
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
            }else {
                return false;
            }

            if(result==null){
                return false;
            }
            if(result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().grant(permission).complete();
            if(result==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llHasMemberPermissionOverride(TextChannel textChannel,Member member){
        try{
            PermissionOverride result = null;
            if(textChannel==null||textChannel.getPermissionOverride(member) == null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetMemberPermissionOverride(TextChannel textChannel,Member member){
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            if(result==null){
                return false;
            }
            result.getManager().reset().complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetAllowMemberPermissionOverride(TextChannel textChannel,Member member){
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            if(result==null){
                return false;
            }
            result.getManager().resetAllow().complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetDenyMemberPermissionOverride(TextChannel textChannel,Member member){
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            if(result==null){
                return false;
            }
            result.getManager().resetDeny().complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default PermissionOverride llGetMemberPermissionOverride(TextChannel textChannel,Member member){
        String fName="[getMemberPermissionOverrid]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }

    }
    default boolean llSetMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission, int mode, String reason){
        String fName="[setMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(mode==-1){
                result.getManager().deny(permission).reason(reason).complete();
            }else
            if(mode==0){
                result.getManager().clear(permission).reason(reason).complete();
            }else
            if(mode==1){
                result.getManager().grant(permission).reason(reason).complete();
            }else{
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    default boolean llDenyMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission, String reason){
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
               return false;
            }
            if(result.getDenied().contains(permission)){
                return false;
            }
            result.getManager().deny(permission).reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llClearMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission, String reason){
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().clear(permission).reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llGrantMemberPermissionOverride(TextChannel textChannel,Member member, Permission permission, String reason){
        try{
            PermissionOverride result = null;
            if(textChannel!=null){
                if (textChannel.getPermissionOverride(member) == null){
                    result=textChannel.createPermissionOverride(member).complete();
                }else{
                    result=textChannel.getPermissionOverride(member);
                }
            }else{

                return false;
            }

            if(result==null){
                return false;
            }
            if(result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().grant(permission).reason(reason).complete();
            if(result==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    default boolean llResetMemberPermissionOverride(TextChannel textChannel,Member member, String reason){
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            result.getManager().reset().reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetAllowMemberPermissionOverride(TextChannel textChannel,Member member, String reason){
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            result.getManager().resetAllow().reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetDenyMemberPermissionOverride(TextChannel textChannel,Member member, String reason){
        try{
            PermissionOverride result = null;
            if(textChannel!=null&&textChannel.getPermissionOverride(member) != null){
                result=textChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            result.getManager().resetDeny().reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    default boolean llSetMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission, int mode){
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
                if(result==null){
                    return false;
                }
                if(mode==-1){
                    result.getManager().deny(permission).complete();
                }else
                if(mode==0){
                    result.getManager().clear(permission).complete();
                }else
                if(mode==1){
                    result.getManager().grant(permission).complete();
                }else{
                    return false;
                }
                if(result==null){
                    ;return false;
                }
                return true;
            }else{

                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    default boolean llDenyMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(result.getDenied().contains(permission)){
                return false;
            }
            result.getManager().deny(permission).complete();
            if(result==null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llClearMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }

            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().clear(permission).complete();
            if(result==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llGrantMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
            }else {
                return false;
            }

            if(result==null){
                return false;
            }
            if(result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().grant(permission).complete();
            if(result==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llHasMemberPermissionOverride(VoiceChannel voiceChannel,Member member){
        try{
            PermissionOverride result = null;
            if(voiceChannel==null||voiceChannel.getPermissionOverride(member) == null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetMemberPermissionOverride(VoiceChannel voiceChannel,Member member){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            if(result==null){
                return false;
            }
            result.getManager().reset().complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetAllowMemberPermissionOverride(VoiceChannel voiceChannel,Member member){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            if(result==null){
                return false;
            }
            result.getManager().resetAllow().complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetDenyMemberPermissionOverride(VoiceChannel voiceChannel,Member member){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            if(result==null){
                return false;
            }
            result.getManager().resetDeny().complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default PermissionOverride llGetMemberPermissionOverride(VoiceChannel voiceChannel,Member member){
        String fName="[getMemberPermissionOverrid]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }

    }
    default boolean llSetMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission, int mode, String reason){
        String fName="[setMemberPermissionOverride]";
        //mode -1 restrict 0 none 1 allow
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(mode==-1){
                result.getManager().deny(permission).reason(reason).complete();
            }else
            if(mode==0){
                result.getManager().clear(permission).reason(reason).complete();
            }else
            if(mode==1){
                result.getManager().grant(permission).reason(reason).complete();
            }else{
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    default boolean llDenyMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission, String reason){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(result.getDenied().contains(permission)){
                return false;
            }
            result.getManager().deny(permission).reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llClearMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission, String reason){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
            }else{
                return false;
            }

            if(result==null){
                return false;
            }
            if(!result.getDenied().contains(permission)&&!result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().clear(permission).reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llGrantMemberPermissionOverride(VoiceChannel voiceChannel,Member member, Permission permission, String reason){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null){
                if (voiceChannel.getPermissionOverride(member) == null){
                    result=voiceChannel.createPermissionOverride(member).complete();
                }else{
                    result=voiceChannel.getPermissionOverride(member);
                }
            }else{

                return false;
            }

            if(result==null){
                return false;
            }
            if(result.getAllowed().contains(permission)){
                return false;
            }
            result.getManager().grant(permission).reason(reason).complete();
            if(result==null){
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    default boolean llResetMemberPermissionOverride(VoiceChannel voiceChannel,Member member, String reason){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            result.getManager().reset().reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetAllowMemberPermissionOverride(VoiceChannel voiceChannel,Member member, String reason){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            result.getManager().resetAllow().reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    default boolean llResetDenyMemberPermissionOverride(VoiceChannel voiceChannel,Member member, String reason){
        try{
            PermissionOverride result = null;
            if(voiceChannel!=null&&voiceChannel.getPermissionOverride(member) != null){
                result=voiceChannel.getPermissionOverride(member);
            }else{
                return false;
            }
            result.getManager().resetDeny().reason(reason).complete();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
