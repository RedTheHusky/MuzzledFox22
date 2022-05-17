package web.jexpress;
import express.Express;
import express.http.response.Response;
import models.lcGlobalHelper;
import org.apache.log4j.Logger;
import java.io.IOException;

import java.util.*;

public class ServerExpress {
    //https://github.com/Simonwep/java-express
    Logger logger = Logger.getLogger(getClass());
    //8788 debug
    //8789 main
    Express app;boolean isRunning=false;int port=8789;
    lcGlobalHelper gGlobal;
    public ServerExpress( lcGlobalHelper global) throws IOException {
        //8789,8788
        String fName="[build]";
        gGlobal=global;
        Express app = new Express();

        app.bind( new expressOAuth(gGlobal,true), new expressRD(gGlobal), new expressLovense(gGlobal),new expressRoots(gGlobal));

        /*if(llGlobalHelper.llBotToken==llGlobalHelper.llMainBotToke){
            port=8789;
        }else{
            port=8788;
        }*/
        app.listen(port);
        logger.info(fName+"Running! Point your browsers to http://localhost:"+port+"/ ");
        isRunning=true;
    }
    public boolean start() {
        String fName="[start]";
        try {
            if(isRunning){
                logger.warn(fName+"Cant as already running");
                return  true;
            }
            app.bind(new expressRoots(gGlobal));
            app.listen(port);
            logger.info(fName+"Running! Point your browsers to http://localhost:"+port+"/ ");
            return  true;
        }catch (Exception e){
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return  false;
        }
    }
    public boolean stop() {
        String fName = "[stop]";
        try {
            if (!isRunning) {
                logger.warn("Cant as already stopped");
                return true;
            }
            app.listen(8789);
            logger.info("Stopped");
            return true;
        } catch (Exception e) {
            logger.error(fName + ".exception=" + e);
            logger.error(fName + ".exception:" + Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

}
