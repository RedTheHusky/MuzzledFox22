package models.lc.interaction.applicationcommand;

import nsfw.diaper.iDiaperInteractive;
import org.apache.log4j.Logger;

import java.util.Arrays;

public interface liApplicationCommand {
    public enum lcApplicationTypes {
        INVALID(-1, ""),
        CHAT_INPUT(1, "chat_input"),
        USER(2, "user"),
        MESSAGE(3, "message");
        private String string;
        private int code;

        private lcApplicationTypes(int code, String string) {
            this.code = code;
            this.string = string;
        }

        public static lcApplicationTypes valueByCode(int code) {
            lcApplicationTypes[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                lcApplicationTypes status = var1[var3];
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }

        public static lcApplicationTypes valueByString(String string) {
            lcApplicationTypes[] var1 = values();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                lcApplicationTypes status = var1[var3];
                if (status.string.equals(string)) {
                    return status;
                }
            }
            return null;
        }

        public String getString() {
            return this.string;
        }

        public int getCode() {
            return this.code;
        }

        static {
            lcApplicationTypes[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                lcApplicationTypes s = var0[var2];
            }

        }

        public static String getString(iDiaperInteractive.DIAPERTYPE level) {
            String fName = "[getString]";
            Logger logger = Logger.getLogger(nsfw.diaper.iDiaperInteractive.class);
            try {
                return level.getString();
            } catch (Exception e) {
                logger.error(fName + ".exception=" + e);
                logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
                return "";
            }
        }


    }
}
