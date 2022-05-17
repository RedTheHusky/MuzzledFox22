package models.ls;

import models.ll.colors.llColor_White;
import models.ll.colors.llColors_Orange;
import models.ll.colors.llColors_Red;
import models.ll.colors.llColors_Yellow;
import net.dv8tion.jda.api.entities.Guild;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Arrays;

public interface lsColorHelper  {
    static Color getColor(int r, int g, int b){
        String fName="[getColor]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".r=" + r+", g="+g+", b="+b);
            Color color=new Color(r,g,b);
            logger.info(fName+".RGB=" + color.getRGB());
            logger.info(fName+".hex=" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            return  color;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Color getColor(int rgb){
        String fName="[getColor]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".rgb=" + rgb);
            Color color=new Color(rgb);
            logger.info(fName+".r=" + color.getRed()+", g="+color.getGreen()+", b="+color.getBlue());
            logger.info(fName+".hex=" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            return  color;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Color getColor(String hex){
        String fName="[getColor]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".hex=" +hex);
            if(!hex.startsWith("#")){
                hex="#"+hex;
            }
            int r=Integer.valueOf( hex.substring( 1, 3 ), 16 );
            int g=Integer.valueOf( hex.substring( 3, 5 ), 16 );
            int b=Integer.valueOf( hex.substring( 5, 7 ), 16 );
            logger.info(fName+".r=" + r+", g="+g+", b="+b);
            Color color=new Color(r,g,b);
            /*hex=hex.replaceAll("#","");
            Color color=Color.decode(hex);
            logger.info(fName+".r=" + color.getRed()+", g="+color.getGreen()+", b="+color.getBlue());*/
            logger.info(fName+".rgb=" + color.getRGB());
            return  color;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }

    static String getHex(int r, int g, int b){
        String fName="[getHex]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".r=" + r+", g="+g+", b="+b);
            Color color=new Color(r,g,b);
            logger.info(fName+".RGB=" + color.getRGB());
            logger.info(fName+".hex=" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            return  String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String getHex(int rgb){
        String fName="[getHex]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".rgb=" + rgb);
            Color color=new Color(rgb);
            logger.info(fName+".r=" + color.getRed()+", g="+color.getGreen()+", b="+color.getBlue());
            logger.info(fName+".hex=" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            return  String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static String getHex(Color color){
        String fName="[getHex]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".r=" + color.getRed()+", g="+color.getGreen()+", b="+color.getBlue());
            logger.info(fName+".rgb=" + color.getRGB());
            logger.info(fName+".hex=" + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
            return  String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Color hex2Color(String colorStr) {
        String fName="[hex2Color]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".colorStr=" +colorStr);
            int red=Integer.valueOf( colorStr.substring( 1, 3 ), 16 );
            int green=Integer.valueOf( colorStr.substring( 3, 5 ), 16 );
            int blue=Integer.valueOf( colorStr.substring( 5, 7 ), 16 );
            logger.info(fName+".red=" +red+", green="+green+", blue="+blue);
            Color color=new Color(red,green,blue);
            return color;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    static Color hex2Color(String colorStr,Color backup) {
        String fName="[hex2Color]";
        Logger logger = Logger.getLogger(lsColorHelper.class);
        try{
            logger.info(fName+".colorStr=" +colorStr);
            logger.info(fName+".backup.rgb=" +backup.getRGB());
            int red=Integer.valueOf( colorStr.substring( 1, 3 ), 16 );
            int green=Integer.valueOf( colorStr.substring( 3, 5 ), 16 );
            int blue=Integer.valueOf( colorStr.substring( 5, 7 ), 16 );
            logger.info(fName+".red=" +red+", green="+green+", blue="+blue);
            Color color=new Color(red,green,blue);
            return color;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+ ".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
}
