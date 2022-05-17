package models.lc.helper;


import models.lcGlobalHelper;
import models.ls.lsStreamHelper;
import net.dv8tion.jda.api.entities.Icon;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class lcImages4Web {
    Logger logger = Logger.getLogger(getClass()); String cName="[lcImages4Web]";
    lcGlobalHelper gGlobal;
    String gSource ="";
    public lcImages4Web(String source){
        String fName="[constructor1]";
        logger.info(cName + fName + ".source=" + source);
        getInputStream(gSource);

    }
    InputStream gIS =null;URL gUrl;HttpURLConnection gHttpcon;
    private boolean getImageInputStream(URL url){
        String fName="[getImageInputStream]";
        try {
            gHttpcon = (HttpURLConnection)url.openConnection();
            gHttpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            int code=gHttpcon.getResponseCode();
            logger.info(fName+".code="+ code);
            if(code>299){
                logger.warn(fName+".the return code is:"+code);
                return  false;
            }
            gIS =gHttpcon.getInputStream();
            if(gIS ==null){
                logger.warn(fName+".inputsource is null");
                return  false;
            }
            return  true;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public InputStream getInputStream(String source){
        String fName="[getInputStream]";
        try {
            logger.info(fName+".source="+ source);
            gSource=source;
            gUrl = new URL(source);
            if(getImageInputStream(gUrl)){
                logger.info(fName+".success get");
            }
            return gIS;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public InputStream getInputStream(URL url){
        String fName="[getInputStream]";
        try {
            logger.info(fName+".with url provided");
            gUrl = url;
            gSource=gUrl.getPath();
            logger.info(fName+".source="+gSource);
            if(getImageInputStream(gUrl)){
                logger.info(fName+".success get");
            }
            return gIS;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    public InputStream getInputStream(){
        String fName="[getInputStream]";
        try {
            if(gIS !=null){
                return gIS;
            }
            else if(gUrl!=null){
                return getInputStream(gUrl);
            }
            else if(gSource!=null&&!gSource.isBlank()){
                return getInputStream(gSource);
            }
            return  null;
        }catch (Exception e){
            logger.error(fName+".exception=" + e);
            logger.error(fName+".exception:" + Arrays.toString(e.getStackTrace()));
            return  null;
        }
    }
    Icon gIcon;
    public Icon getIcon(InputStream is){
        String fName="[getIcon]";
        try{
            gSource="";gUrl=null;
            gIcon=Icon.from(is);
            logger.info(fName+"done");
            return gIcon;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Icon getIcon(String source){
        String fName="[getIcon]";
        try{
            logger.info(fName+".source="+ source);
            gSource=source;
            gUrl = new URL(source);
            if(!getImageInputStream(gUrl)){
                logger.warn(fName+".failed to get");
                return  null;
            }
            return getIcon(gIS);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Icon getIcon(URL url){
        String fName="[getIcon]";
        try{
            logger.info(fName+".with url provided");
            gUrl = url;
            gSource=gUrl.getPath();
            logger.info(fName+".source="+gSource);
            if(!getImageInputStream(gUrl)){
                logger.warn(fName+".failed to get");
                return  null;
            }
            return getIcon(gIS);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    public Icon getIcon(){
        String fName="[getIcon]";
        try{
            if(gIcon!=null){
                return gIcon;
            }
            if(gIS !=null){
                return getIcon(gIS);
            }
            else if(gUrl!=null&&getInputStream(gUrl)!=null){
                return getIcon(gIS);
            }
            else if(gSource!=null&&!gSource.isBlank()&&getInputStream(gSource)!=null){
                return getIcon(gIS);
            }
            return  null;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }
    private boolean saveImageDo(String destinationFile){
        String fName="[saveImageDo]";
        try{
            logger.info(fName+"destinationFile="+destinationFile);
            OutputStream os = new FileOutputStream(destinationFile);
            byte[] b = new byte[2048];
            int length;
            while ((length = gIS.read(b)) != -1) {
                os.write(b, 0, length);
            }
            os.close();
            return true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveImage(String destinationFile){
        String fName="[saveImage]";
        try{
            if(gIS !=null){
                return saveImageDo(destinationFile);
            }
            else if(gUrl!=null&&getInputStream(gUrl)!=null){
                return saveImageDo(destinationFile);
            }
            else if(gSource!=null&&!gSource.isBlank()&&getInputStream(gSource)!=null){
                return saveImageDo(destinationFile);
            }
            return  false;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveImage(URL url,String destinationFile){
        String fName="[saveImage]";
        try{
            logger.info(fName+".with url provided");
            gUrl = url;
            gSource=gUrl.getPath();
            logger.info(fName+".source="+gSource);
            if(!getImageInputStream(gUrl)){
                logger.warn(fName+".failed to get");
                return  false;
            }
            return saveImageDo(destinationFile);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveImage(String source,String destinationFile){
        String fName="[saveImage]";
        try{
            logger.info(fName+"source="+source+", destinationFile="+destinationFile);
            gSource=source;
            gUrl = new URL(source);
            if(!getImageInputStream(gUrl)){
                logger.warn(fName+".failed to get");
                return  false;
            }
            return saveImageDo(destinationFile);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
    public boolean saveImage(InputStream is,String destinationFile){
        String fName="[saveImage]";
        try{
            gSource="";gUrl=null;
            gIS=is;
            return saveImageDo(destinationFile);
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }
}
