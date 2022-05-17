package models.ls;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.image.*;
import java.util.Arrays;

public interface lsImageConvert {
    //https://stackoverflow.com/questions/665406/how-to-make-a-color-transparent-in-a-bufferedimage-and-save-as-png/665483
    //http://www.java2s.com/Code/Java/2D-Graphics-GUI/Providesusefulmethodsforconvertingimagesfromonecolourdepthtoanother.htm
    /**
     * Converts the source to 1-bit colour depth (monochrome). No transparency.
     *
     * @param src
     *            the source image to convert
     * @return a copy of the source image with a 1-bit colour depth.
     */
    static BufferedImage convert1(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert1";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(1, 2, new byte[] { (byte) 0,
                    (byte) 0xFF }, new byte[] { (byte) 0, (byte) 0xFF },
                    new byte[] { (byte) 0, (byte) 0xFF });
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY, icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert1Transparency(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert1Transparency";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(1, 2, new byte[] { (byte) 0,
                    (byte) 0xFF }, new byte[] { (byte) 0, (byte) 0xFF },
                    new byte[] { (byte) 0, (byte) 0xFF });
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY, icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);

            Graphics g = dest.getGraphics();
            //g.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    int CGA_Black= 0x000000, CGA_DarkRed= 0x800000,CGA_DarkGreen= 0x008000, CGA_DarkYellow=0x808000,CGA_DarkBlue=0x000080,CGA_DarkMagenta=0x800080,CGA_DarkCyan=0x008080,CGA_DarkGrey= 0x808080;
    int CGA_LightGrey=0xC0C0C0,CGA_Red=0xFF0000,CGA_Green=0x00FF00,CGA_Yellow= 0xFFFF00,CGA_Blue=0x0000FF,CGA_Magenta=0xFF00FF,CGA_Cyan= 0x00FFFF,CGA_White=0xFFFFFF;
    static int[] CGAPalette(int mode, boolean high) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="CGAPalette";
        try {
            logger.info(fName+".init: mode="+mode+", hight="+high);
            int[] cmap;
            if(mode==1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {CGA_Black,CGA_Green,CGA_Red,CGA_Yellow};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,CGA_DarkGreen,CGA_DarkRed,CGA_DarkYellow};
                }
            }else
            if(mode==-1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {CGA_Black,CGA_Red,CGA_Green,CGA_Yellow};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,CGA_DarkRed,CGA_DarkGreen,CGA_DarkYellow};
                }
            }else
            if(mode==-2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Magenta,CGA_Cyan,CGA_White};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,CGA_DarkMagenta,CGA_DarkCyan,CGA_LightGrey};
                }
            }else
            if(mode==2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Magenta,CGA_White};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,CGA_DarkCyan,CGA_DarkMagenta,CGA_LightGrey};
                }
            }else
            if(mode==3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Red,CGA_Cyan,CGA_White};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,CGA_DarkRed,CGA_DarkCyan,CGA_LightGrey};
                }
            }else
            if(mode==-3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Red,CGA_White};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,CGA_DarkCyan,CGA_DarkRed,CGA_LightGrey};
                }
            }else
            if(mode==4){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Green,CGA_Red,CGA_Magenta,CGA_Yellow,CGA_White};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,CGA_DarkCyan,CGA_DarkGreen,CGA_DarkRed,CGA_DarkMagenta,CGA_DarkYellow,CGA_LightGrey};
                }
            }else
            if(mode==5){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Green,CGA_Red,CGA_Magenta,CGA_Yellow,CGA_White,CGA_LightGrey,CGA_Blue};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,CGA_DarkCyan,CGA_DarkGreen,CGA_DarkRed,CGA_DarkMagenta,CGA_DarkYellow,CGA_DarkGrey,CGA_DarkBlue};
                }
            }else{
                cmap = new int[] { CGA_Black, CGA_DarkRed,CGA_DarkGreen, CGA_DarkYellow,CGA_DarkBlue,CGA_DarkMagenta,CGA_DarkCyan,CGA_DarkGrey,
                        CGA_LightGrey,CGA_Red,CGA_Green,CGA_Yellow,CGA_Blue,CGA_Magenta,CGA_Cyan,CGA_White};
            }
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    static int[] CGAPalette() {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="CGAPalette";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { CGA_Black, CGA_DarkRed,CGA_DarkGreen, CGA_DarkYellow,CGA_DarkBlue,CGA_DarkMagenta,CGA_DarkCyan,CGA_DarkGrey,
                    CGA_LightGrey,CGA_Red,CGA_Green,CGA_Yellow,CGA_Blue,CGA_Magenta,CGA_Cyan,CGA_White};
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    int C64_Black=0x000000,C64_White=0xFFFFFF,C64_Red=0x880000,C64_Cyan=0xAAFFEE,C64_Violet=0xCC44CC,C64_Green=0x00CC55,C64_Blue=0x0000AA,C64_Yellow=0xEEEE77, C64_Orange=0xDD8855,C64_Brown=0x664400,C64_DarkGrey=0x333333,
    C64_LightRed=0xFF7777,C64_Grey=0x777777,C64_LightGreen=0xAAFF66,C64_LightBlue=0x0088FF,C64_LightGrey=0xBBBBBB;
    static int[] C64Palette() {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="CGAPalette";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { C64_Black,C64_White,C64_Red,C64_Cyan,C64_Violet,C64_Green,C64_Blue,C64_Yellow, C64_Orange,C64_Brown,
                    C64_LightRed,C64_DarkGrey,C64_Grey,C64_LightGreen,C64_LightBlue,C64_LightGrey};
            //cmap = new int[] { C64_Black,C64_Red,C64_Green,C64_Orange,C64_Blue,C64_Violet,C64_Cyan,C64_DarkGrey,C64_Grey,C64_LightGrey,C64_LightRed,C64_LightGreen,C64_Yellow,C64_LightBlue};
            //cmap = new int[] { C64_Black,C64_Red,C64_Green,C64_Brown,C64_Orange,C64_Blue,C64_Violet,C64_Cyan,C64_DarkGrey,C64_Grey,C64_LightGrey,C64_LightRed,C64_LightGreen,C64_Yellow,C64_LightBlue,C64_White};
            //cmap = new int[] { C64_Black, C64_Red,C64_Green, C64_Yellow,C64_Blue,C64_Violet,C64_Cyan,C64_DarkGrey,C64_Grey,C64_LightRed,C64_LightGreen,C64_Brown,C64_LightBlue,C64_Orange,C64_LightGrey,C64_White};

            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    static int[] C64Palette(int mode, boolean high) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="C64Palette";
        try {
            logger.info(fName+".init: mode="+mode+", high="+high);
            int[] cmap ;
            if(mode==1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {C64_Black,C64_LightGreen,C64_LightRed,C64_Orange};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,C64_Green,C64_Red,C64_Yellow};
                }
            }else
            if(mode==-1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {CGA_Black,C64_LightRed,C64_LightGreen,C64_Orange};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,C64_Red,C64_Green,C64_Yellow};
                }
            }else
            if(mode==-2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Magenta,CGA_Cyan,C64_LightGrey};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_Violet,C64_Cyan,C64_DarkGrey};
                }
            }else
            if(mode==2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Magenta,C64_LightGrey};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_Cyan,C64_Violet,C64_DarkGrey};
                }
            }else
            if(mode==3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,C64_LightRed,CGA_Cyan,C64_LightGrey};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_Red,C64_Cyan,C64_DarkGrey};
                }
            }else
            if(mode==-3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_LightRed,C64_LightGrey};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_Cyan,C64_Red,C64_DarkGrey};
                }
            }else
            if(mode==4){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_LightGreen,C64_LightRed,CGA_Magenta,C64_Yellow,C64_LightGrey};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,C64_Cyan,C64_Green,C64_Red,C64_Violet,C64_Orange,C64_DarkGrey};
                }
            }else
            if(mode==5){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_LightGreen,C64_LightRed,CGA_Magenta,C64_Yellow,CGA_White,C64_LightGrey,C64_LightBlue};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,C64_Cyan,C64_Green,C64_Red,C64_Violet,C64_Orange,C64_DarkGrey,C64_Blue};
                }
            }else{
                cmap = new int[] { C64_Black,C64_White,C64_Red,C64_Cyan,C64_Violet,C64_Green,C64_Blue,C64_Yellow, C64_Orange,C64_Brown,
                        C64_LightRed,C64_DarkGrey,C64_Grey,C64_LightGreen,C64_LightBlue,C64_LightGrey};
            }
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    int C64_2Red=0x68372B, C64_2Cyan=0x70A4B2, C64_2Violet=0x6F3D86, C64_2Green=0x588D43, C64_2Blue=0x352879, C64_2Yellow=0xB8C76F, C64_2Orange=0x6F4F25, C64_2Brown=0x433900, C64_2LightRed=0x9A6759, C64_2DarkGrey=0x444444, C64_2Grey=0x6C6C6C, C64_2LightGreen=0x9AD284, C64_2LightBlue=0x6C5EB5, C64_2LightGrey=0x959595;
    static int[] C64_2_Palette() {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="C64_2_Palette";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { C64_Black,C64_White,C64_2Red,C64_2Cyan,C64_2Violet,C64_2Green,C64_2Blue,C64_2Yellow, C64_2Orange,C64_2Brown,
                    C64_2LightRed,C64_2DarkGrey,C64_2Grey,C64_2LightGreen,C64_2LightBlue,C64_2LightGrey};
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    static int[] C64_2_Palette(int mode, boolean high) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="C64_2_Palette";
        try {
            logger.info(fName+".init: mode="+mode+", high="+high);
            int[] cmap ;
            if(mode==1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {C64_Black,C64_2LightGreen,C64_2LightRed,C64_2Orange};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,C64_2Green,C64_2Red,C64_2Yellow};
                }
            }else
            if(mode==-1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {CGA_Black,C64_2LightRed,C64_2LightGreen,C64_2Orange};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,C64_2Red,C64_2Green,C64_2Yellow};
                }
            }else
            if(mode==-2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Magenta,CGA_Cyan,C64_2LightGrey};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_2Violet,C64_2Cyan,C64_2DarkGrey};
                }
            }else
            if(mode==2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Magenta,C64_2LightGrey};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_2Cyan,C64_2Violet,C64_2DarkGrey};
                }
            }else
            if(mode==3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,C64_2LightRed,CGA_Cyan,C64_2LightGrey};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_2Red,C64_2Cyan,C64_2DarkGrey};
                }
            }else
            if(mode==-3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_2LightRed,C64_2LightGrey};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_2Cyan,C64_2Red,C64_2DarkGrey};
                }
            }else
            if(mode==4){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_2LightGreen,C64_2LightRed,CGA_Magenta,C64_2Yellow,C64_2LightGrey};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,C64_2Cyan,C64_2Green,C64_2Red,C64_2Violet,C64_2Orange,C64_2DarkGrey};
                }
            }else
            if(mode==5){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_2LightGreen,C64_2LightRed,CGA_Magenta,C64_2Yellow,CGA_White,C64_2LightGrey,C64_2LightBlue};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,C64_2Cyan,C64_2Green,C64_2Red,C64_2Violet,C64_2Orange,C64_2DarkGrey,C64_2Blue};
                }
            }else{
                cmap = new int[] { C64_Black,C64_White,C64_2Red,C64_2Cyan,C64_2Violet,C64_2Green,C64_2Blue,C64_2Yellow, C64_2Orange,C64_2Brown,
                        C64_2LightRed,C64_2DarkGrey,C64_2Grey,C64_2LightGreen,C64_2LightBlue,C64_2LightGrey};
            }
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    int C64_3Red=0x9f4e44, C64_3Cyan=0x6abfc6, C64_3Violet=0xa057a3, C64_3Green=0x5cab5e, C64_3Blue=0x50459b, C64_3Yellow=0xc9d487, C64_3Orange=0xa1683c, C64_3Brown=0x6d5412, C64_3LightRed=0xcb7e75, C64_3DarkGrey=0x626262, C64_3Grey=0x898989, C64_3LightGreen=0x9ae29b, C64_3LightBlue=0x887ecb, C64_3LightGrey=0xadadad;
    static int[] C64_3_Palette() {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="C64_3_Palette";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { C64_Black,C64_White,C64_3Red,C64_3Cyan,C64_3Violet,C64_3Green,C64_3Blue,C64_3Yellow, C64_3Orange,C64_3Brown,
                    C64_3LightRed,C64_3DarkGrey,C64_3Grey,C64_3LightGreen,C64_3LightBlue,C64_3LightGrey};
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    static int[] C64_3_Palette(int mode, boolean high) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="C64_3_Palette";
        try {
            logger.info(fName+".init: mode="+mode+", high="+high);
            int[] cmap ;
            if(mode==1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {C64_Black,C64_3LightGreen,C64_3LightRed,C64_3Orange};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,C64_3Green,C64_3Red,C64_3Yellow};
                }
            }else
            if(mode==-1){
                if(high){
                    //black, green, red, yellow
                    cmap = new int[] {CGA_Black,C64_3LightRed,C64_3LightGreen,C64_3Orange};
                }else{
                    //black, dark-green, dark-red, brown
                    cmap = new int[] {CGA_Black,C64_3Red,C64_3Green,C64_3Yellow};
                }
            }else
            if(mode==-2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Magenta,CGA_Cyan,C64_3LightGrey};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_3Violet,C64_3Cyan,C64_3DarkGrey};
                }
            }else
            if(mode==2){
                if(high){
                    //black, magenta, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,CGA_Magenta,C64_3LightGrey};
                }else{
                    //black, dark-magenta, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_3Cyan,C64_3Violet,C64_3DarkGrey};
                }
            }else
            if(mode==3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,C64_3LightRed,CGA_Cyan,C64_3LightGrey};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_3Red,C64_3Cyan,C64_3DarkGrey};
                }
            }else
            if(mode==-3){
                if(high){
                    //black, red, cyan, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_3LightRed,C64_3LightGrey};
                }else{
                    //black, dark-red, dark-cyan, gray
                    cmap = new int[] {CGA_Black,C64_3Cyan,C64_3Red,C64_3DarkGrey};
                }
            }else
            if(mode==4){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_3LightGreen,C64_3LightRed,CGA_Magenta,C64_3Yellow,C64_3LightGrey};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,C64_3Cyan,C64_3Green,C64_3Red,C64_3Violet,C64_3Orange,C64_3DarkGrey};
                }
            }else
            if(mode==5){
                if(high){
                    //black,cyan, geen, red, magenta, yellow, white
                    cmap = new int[] {CGA_Black,CGA_Cyan,C64_3LightGreen,C64_3LightRed,CGA_Magenta,C64_3Yellow,CGA_White,C64_3LightGrey,C64_3LightBlue};
                }else{
                    //black,d-cyan, d-geen, d-red, d-magenta, brown, gray
                    cmap = new int[] {CGA_Black,C64_3Cyan,C64_3Green,C64_3Red,C64_3Violet,C64_3Orange,C64_3DarkGrey,C64_3Blue};
                }
            }else{
                cmap = new int[] { C64_Black,C64_White,C64_3Red,C64_3Cyan,C64_3Violet,C64_3Green,C64_3Blue,C64_3Yellow, C64_3Orange,C64_3Brown,
                        C64_3LightRed,C64_3DarkGrey,C64_3Grey,C64_3LightGreen,C64_3LightBlue,C64_3LightGrey};
            }
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    static int[] Mix_1_Palette() {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="Mix_1_Palette";
        try {
            logger.info(fName+".init");
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_Orange,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3LightGrey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,CGA_Cyan,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Orange,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3LightGrey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,CGA_Cyan,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Brown,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3LightGrey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,CGA_Cyan,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Orange,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,C64_3LightGrey,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Brown,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Orange,C64_3LightBlue,C64_3Yellow,C64_3LightGrey,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Yellow,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Orange,C64_3LightBlue,C64_3Brown,C64_3LightGrey,CGA_White};*/
            int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Yellow,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Orange,C64_3LightBlue,CGA_Cyan,C64_3LightGrey,CGA_White};
            /*int[] cmap = new int[] { C64_Black,C64_White,C64_3Red,C64_3Cyan,C64_3Violet,C64_3Green,C64_3Blue,C64_3Yellow, C64_3Orange,CGA_Cyan,
                    C64_3LightRed,C64_3DarkGrey,C64_3Grey,C64_3LightGreen,C64_3LightBlue,C64_3LightGrey};*/
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    static int[] Mix_1_Palette(int mode, boolean high) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="Mix_1_Palette";
        try {
            logger.info(fName+".init");
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_Orange,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3LightGrey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,CGA_Cyan,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Orange,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3LightGrey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,CGA_Cyan,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Brown,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3LightGrey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,CGA_Cyan,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Orange,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Yellow,C64_3LightBlue,CGA_Magenta,C64_3LightGrey,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Brown,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Orange,C64_3LightBlue,C64_3Yellow,C64_3LightGrey,CGA_White};*/
            /*int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Yellow,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Orange,C64_3LightBlue,C64_3Brown,C64_3LightGrey,CGA_White};*/
            int[] cmap = new int[] { CGA_Black, C64_3Red,C64_3Green, C64_3Yellow,C64_3Blue,C64_3Violet,C64_3Cyan,C64_3DarkGrey,
                    C64_3Grey,C64_3LightRed,C64_3LightGreen,C64_3Orange,C64_3LightBlue,CGA_Cyan,C64_3LightGrey,CGA_White};
            /*int[] cmap = new int[] { C64_Black,C64_White,C64_3Red,C64_3Cyan,C64_3Violet,C64_3Green,C64_3Blue,C64_3Yellow, C64_3Orange,CGA_Cyan,
                    C64_3LightRed,C64_3DarkGrey,C64_3Grey,C64_3LightGreen,C64_3LightBlue,C64_3LightGrey};*/
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    int Win_Black=0x000000,Win_White=0xFFFFFF,
    Win_Gray=0x7E7E7E,Win_Red=0x7E0000,Win_Yellow=0x7E7E00,Win_Magenta=0x7E007E,Win_Green=0x047E00,Win_Blue=0x00007E,Win_Cyan=0x047E74,
    Win_LightGray=0xBEBEBE,Win_LightRed=0xFE0000,Win_LightYellow=0xFFFF04,Win_LightMagenta=0xFE00FF,Win_LightGreen=0x06FF04,Win_LightBlue=0x0000FF,Win_LightCyan=0x06FFFF;
    int PC88_Black=0x000000, PC88_Gray=0xdbdbdb,PC88_Red=0xdb0000,PC88_LightRed=0xff926d,PC88_Yellow=0xffb600,PC88_Green=0x00db6d,PC88_Cyan=0x00b6db,PC88_Blue=0x0000db;
    int ZXSpectrum_Black=0x000000, ZXSpectrum_Blue=0x0022c7, ZXSpectrum_LightBlue=0x002bfb, ZXSpectrum_Red=0xd62816, ZXSpectrum_LightRed=0xff331c, ZXSpectrum_Magenta=0xd433c7, ZXSpectrum_LightMagenta=0xff40fc, ZXSpectrum_Green=0x00c525, ZXSpectrum_LightGreem=0x00f92f, ZXSpectrum_Cyan=0x00c7c9, ZXSpectrum_LightCyan=0x00fbfe, ZXSpectrum_Yellow=0xccc82a, ZXSpectrum_LightYellow=0xfffc36, ZXSpectrum_Gray=0xcacaca, ZXSpectrum_White=0xffffff;
    int Colodore_Black=0x000000, Colodore_DarkGray=0x4a4a4a, Colodore_Gray=0x7b7b7b, Colodore_LightGray=0xb2b2b2, Colodore_White=0xffffff, Colodore_Red=0x813338, Colodore_LightRed=0xc46c71, Colodore_Brown=0x553800, Colodore_Orange=0x8e5029, Colodore_Yellow=0xedf171, Colodore_LightGreen=0xa9ff9f, Colodore_Green=0x56ac4d, Colodore_Cyan=0x75cec8, Colodore_LightBlue=0x706deb, Colodore_Blue=0x2e2c9b, Colodore_Magenta=0x8e3c97;
    static int[] Colodore_Palette() {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="Colodore_Palette";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { Colodore_Black,Colodore_White,Colodore_Red,C64_3Cyan,Colodore_Magenta,Colodore_Green,Colodore_Blue,Colodore_Yellow, Colodore_Orange,Colodore_Brown,
                    Colodore_LightRed,Colodore_DarkGray,Colodore_Gray,Colodore_LightGreen,Colodore_LightBlue,Colodore_LightGray};
            return cmap;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return new int[] {CGA_Black};
        }
    }
    /**
     * Converts the source image to 4-bit colour using the default 16-colour
     * palette:
     * <ul>
     * <li>black</li>
     * <li>dark red</li>
     * <li>dark green</li>
     * <li>dark yellow</li>
     * <li>dark blue</li>
     * <li>dark magenta</li>
     * <li>dark cyan</li>
     * <li>dark grey</li>
     * <li>light grey</li>
     * <li>red</li>
     * <li>green</li>
     * <li>yellow</li>
     * <li>blue</li>
     * <li>magenta</li>
     * <li>cyan</li>
     * <li>white</li>
     * </ul>
     * No transparency.
     *
     * @param src
     *            the source image to convert
     * @return a copy of the source image with a 4-bit colour depth, with the
     *         default colour pallette
     */
    static BufferedImage convert4(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert4,16color";
        try {
            int[] cmap = new int[] { CGA_Black, CGA_DarkRed,CGA_DarkGreen, CGA_DarkYellow,CGA_DarkBlue,CGA_DarkMagenta,CGA_DarkCyan,CGA_DarkGrey,
                    CGA_LightGrey,CGA_Red,CGA_Green,CGA_Yellow,CGA_Blue,CGA_Magenta,CGA_Cyan,CGA_White};
            return convert4(src, cmap);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert4(BufferedImage src, int mode, boolean high) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert4Transparency,4color";
        try {
            logger.info(fName+".init: mode="+mode+", hight="+high);
            int[] cmap = CGAPalette(mode,high);

            return convert4(src, cmap);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /**
     * Converts the source image to 4-bit colour using the given colour map. No
     * transparency.
     *
     * @param src
     *            the source image to convert
     * @param cmap
     *            the colour map, which should contain no more than 16 entries
     *            The entries are in the form RRGGBB (hex).
     * @return a copy of the source image with a 4-bit colour depth, with the
     *         custom colour pallette
     */
    static BufferedImage convert4(BufferedImage src, int[] cmap) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert4";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(4, cmap.length, cmap, 0,
                    false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY, icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert4Transparency(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert4Transparency,16color";
        try {
            int[] cmap = new int[] { 0x000000, 0x800000, 0x008000, 0x808000,
                    0x000080, 0x800080, 0x008080, 0x808080, 0xC0C0C0, 0xFF0000,
                    0x00FF00, 0xFFFF00, 0x0000FF, 0xFF00FF, 0x00FFFF, 0xFFFFFF };
            return convert4Transparency(src, cmap);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert4Transparency(BufferedImage src, int[] cmap) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert4Transparency";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(4, cmap.length, cmap, 0,
                    false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY, icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);

            Graphics g = dest.getGraphics();
            //g.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, dest.getWidth(), dest.getHeight());

            /*Graphics g2 = src.getGraphics();
            g2.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g2.fillRect(0, 0, src.getWidth(), src.getHeight());*/

            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /**
     * Converts the source image to 8-bit colour using the default 256-colour
     * palette. No transparency.
     *
     * @param src
     *            the source image to convert
     * @return a copy of the source image with an 8-bit colour depth
     */
    static BufferedImage convert8(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert8";
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_INDEXED);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert8Transparency(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert8Transparency";
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_INDEXED);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);

            Graphics g = dest.getGraphics();
            //g.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert8CGA(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert8CGA";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { CGA_Black, CGA_DarkRed,CGA_DarkGreen, CGA_DarkYellow,CGA_DarkBlue,CGA_DarkMagenta,CGA_DarkCyan,CGA_DarkGrey,
                    CGA_LightGrey,CGA_Red,CGA_Green,CGA_Yellow,CGA_Blue,CGA_Magenta,CGA_Cyan,CGA_White};
            return convert8CGA(src,cmap);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert8CGA(BufferedImage src,int[] cmap) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert8CGA";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(8, cmap.length, cmap, 0,
                    true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_INDEXED,icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert8CGATransparency(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert8CGATransparency";
        try {
            logger.info(fName+".init");
            int[] cmap = new int[] { CGA_Black, CGA_DarkRed,CGA_DarkGreen, CGA_DarkYellow,CGA_DarkBlue,CGA_DarkMagenta,CGA_DarkCyan,CGA_DarkGrey,
                    CGA_LightGrey,CGA_Red,CGA_Green,CGA_Yellow,CGA_Blue,CGA_Magenta,CGA_Cyan,CGA_White};
            return convert8CGATransparency(src,cmap);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert8CGATransparency(BufferedImage src,int[] cmap) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert8CGATransparency";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(8, cmap.length, cmap, 0,
                    true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_INDEXED,icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);

            Graphics g = dest.getGraphics();
            //g.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /**
     * Converts the source image to 24-bit colour (RGB). No transparency.
     *
     * @param src
     *            the source image to convert
     * @return a copy of the source image with a 24-bit colour depth
     */
    static BufferedImage convert24(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert24";
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert24(BufferedImage src,int[] cmap) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert24";
        try {
            logger.info(fName+".init");
            IndexColorModel icm = new IndexColorModel(8, cmap.length, cmap, 0,
                    true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_INT_RGB,icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    /**
     * Converts the source image to 32-bit colour with transparency (ARGB).
     *
     * @param src
     *            the source image to convert
     * @return a copy of the source image with a 32-bit colour depth.
     */
    static BufferedImage convert32(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert32";
        try {

            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convert32(BufferedImage src,int[] cmap) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName="convert32";
        try {
            IndexColorModel icm = new IndexColorModel(8, cmap.length, cmap, 0,
                    true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(),
                    BufferedImage.TYPE_BYTE_BINARY,icm);
            ColorConvertOp cco = new ColorConvertOp(src.getColorModel()
                    .getColorSpace(), dest.getColorModel().getColorSpace(), null);
            cco.filter(src, dest);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static BufferedImage convertRGBAToIndexed(BufferedImage src) {
        String fName = "convertRGBAToIndexed"; Logger logger = Logger.getLogger(lsImageConvert.class);
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);//BufferedImage.TYPE_BYTE_INDEXED
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return null;
        }
    }
    static BufferedImage convertRGBAToIndexedTransparency(BufferedImage src) {
        String fName = "convertRGBAToIndexed+Transparency"; Logger logger = Logger.getLogger(lsImageConvert.class);
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);//BufferedImage.TYPE_BYTE_INDEXED
            Graphics g = dest.getGraphics();
            //g.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return null;
        }
    }
    static BufferedImage convertRGBAToIndexedTransparency(BufferedImage src,int rgb) {
        String fName = "convertRGBAToIndexed+Transparency"; Logger logger = Logger.getLogger(lsImageConvert.class);
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);//BufferedImage.TYPE_BYTE_INDEXED
            Graphics g = dest.getGraphics();
            //g.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(rgb)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return null;
        }
    }
    static BufferedImage convertRGBAToIndexedTransparency(BufferedImage src,int r, int g, int b) {
        String fName = "convertRGBAToIndexed+Transparency"; Logger logger = Logger.getLogger(lsImageConvert.class);
        try {
            logger.info(fName+".init");
            BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);//BufferedImage.TYPE_BYTE_INDEXED
            Graphics g2 = dest.getGraphics();
            //g2.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g2.setColor(new Color(r,g,b)); // fill with a hideous color and make it transparent
            g2.fillRect(0, 0, dest.getWidth(), dest.getHeight());
            dest = makeTransparent(dest, 0, 0);
            if(dest==null){logger.error(fName + ".error dest is null after makeTransparent");return  null;}
            dest.createGraphics().drawImage(src, 0, 0, null);
            return dest;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));return null;
        }
    }
    static BufferedImage makeTransparent(BufferedImage src, int x, int y) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName = "makeTransparent";
        try {
            logger.info(fName+".init");
            ColorModel cm = src.getColorModel();
            if (!(cm instanceof IndexColorModel))
                return src; // sorry...
            IndexColorModel icm = (IndexColorModel) cm;
            WritableRaster raster = src.getRaster();
            int pixel = raster.getSample(x, y, 0); // pixel is offset in ICM's palette
            int size = icm.getMapSize();
            byte[] reds = new byte[size];
            byte[] greens = new byte[size];
            byte[] blues = new byte[size];
            icm.getReds(reds);
            icm.getGreens(greens);
            icm.getBlues(blues);
            IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
            return new BufferedImage(icm2, raster, src.isAlphaPremultiplied(), null);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static BufferedImage makeTransparent(BufferedImage src) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName = "makeTransparent";
        try {
            logger.info(fName+".init");
            Graphics g = src.getGraphics();
            //g2.setColor(new Color(0, 0, 0)); //or dont make it transparent
            g.setColor(new Color(231, 20, 189)); // fill with a hideous color and make it transparent
            g.fillRect(0, 0, src.getWidth(), src.getHeight());
            //dest = makeTransparent(dest, 0, 0);
            //dest.createGraphics().drawImage(src, 0, 0, null);
            ColorModel cm = src.getColorModel();
            if (!(cm instanceof IndexColorModel))
                return src; // sorry...
            IndexColorModel icm = (IndexColorModel) cm;
            WritableRaster raster = src.getRaster();
            int pixel = raster.getSample(0, 0, 0); // pixel is offset in ICM's palette
            int size = icm.getMapSize();
            byte[] reds = new byte[size];
            byte[] greens = new byte[size];
            byte[] blues = new byte[size];
            icm.getReds(reds);
            icm.getGreens(greens);
            icm.getBlues(blues);
            IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
            return new BufferedImage(icm2, raster, src.isAlphaPremultiplied(), null);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static BufferedImage pixelate(BufferedImage scr,int PIX_SIZE) {
        Logger logger = Logger.getLogger(lsImageConvert.class);
        String fName = "pixelate";
        try {
            logger.info(fName+".init");
            //10+ too much, 5 ehhh maybe smaller
            // Read the file as an Image
            // Get the raster data (array of pixels)
            Raster raster = scr.getData();
            // Create an identically-sized output raster
            WritableRaster dest = raster.createCompatibleWritableRaster();
            // Loop through every PIX_SIZE pixels, in both x and y directions
            for(int y = 0; y < raster.getHeight(); y += PIX_SIZE) {
                for(int x = 0; x < raster.getWidth(); x += PIX_SIZE) {
                    // Copy the pixel
                    double[] pixel = new double[3];
                    pixel = raster.getPixel(x, y, pixel);
                    // "Paste" the pixel onto the surrounding PIX_SIZE by PIX_SIZE neighbors
                    // Also make sure that our loop never goes outside the bounds of the image
                    for(int yd = y; (yd < y + PIX_SIZE) && (yd < dest.getHeight()); yd++) {
                        for(int xd = x; (xd < x + PIX_SIZE) && (xd < dest.getWidth()); xd++) {
                            dest.setPixel(xd, yd, pixel);
                        }
                    }
                }
            }
            // Save the raster back to the Image
            scr.setData(dest);return scr;
        } catch (Exception e) {
            logger.error( fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    static Image makeColorTransparent(BufferedImage im, final Color color) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName = "makeColorTransparent";
        try {
            ImageFilter filter = new RGBImageFilter() {

                // the color we are looking for... Alpha bits are set to opaque
                public final int markerRGB = color.getRGB() | 0xFF000000;

                public final int filterRGB(int x, int y, int rgb) {
                    if ((rgb | 0xFF000000) == markerRGB) {
                        // Mark the alpha bits as zero - transparent
                        return 0x00FFFFFF & rgb;
                    } else {
                        // nothing to do
                        return rgb;
                    }
                }
            };

            ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
            return Toolkit.getDefaultToolkit().createImage(ip);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static BufferedImage toBufferedImage(Image img) {
        Logger logger = Logger.getLogger(lsImageConvert.class);String fName = "toBufferedImage";
        try {
            if (img instanceof BufferedImage)
            {
                return (BufferedImage) img;
            }

            // Create a buffered image with transparency
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

            // Draw the image on to the buffered image
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();

            // Return the buffered image
            return bimage;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

}
