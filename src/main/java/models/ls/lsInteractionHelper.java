package models.ls;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import static models.llGlobalHelper.llPrefixStr;

public interface lsInteractionHelper {
    static String convertOffsetDateTime2HumanReadable(OffsetDateTime time) {
        String fName="convertOffsetDateTime2HumanReadable.";
        Logger logger = Logger.getLogger(lsChannelHelper.class);
        try {

            return "";
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
