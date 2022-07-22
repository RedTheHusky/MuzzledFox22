package forRemoval.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Sticker;
import com.pengrad.telegrambot.model.StickerSet;
import com.pengrad.telegrambot.request.GetStickerSet;
import com.pengrad.telegrambot.response.GetStickerSetResponse;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class TelegramRoot {
    Logger logger = Logger.getLogger(getClass());
    public TelegramBot bot;
    lcGlobalHelper gGlobal;
    public TelegramRoot(lcGlobalHelper global) throws IOException {
        String fName = "build";
        logger.info(fName + ":Loading telegram bot");
        bot = new TelegramBot("1364622474:AAGCEl5tzQP8XVPYBljs00Zmn3Nz_WG2l9M");
        gGlobal = global;
        logger.info(fName + ": We are live");
    }

    public void getSticker(String name){
        String fName="getSticker";
        try {
            logger.info(fName + "name="+ name);
            GetStickerSet getStickerSet = new GetStickerSet(name);
            GetStickerSetResponse response = bot.execute(getStickerSet);
            StickerSet stickerSet = response.stickerSet();
            Sticker[]stickers=stickerSet.stickers();
            for(int i=0;i<stickers.length;i++){
                Sticker sticker=stickers[i];
                logger.info(fName + "stickers["+i+"].emoji="+sticker.emoji());
                logger.info(fName + "stickers["+i+"].fileID="+sticker.fileId());
                logger.info(fName + "stickers["+i+"].emoji="+sticker.isAnimated());
                PhotoSize photoSize=sticker.thumb();
            }
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
        }
    }


}
