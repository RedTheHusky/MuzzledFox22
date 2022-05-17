package nsfw.chastity.lockedmen;

public interface iLockedmen {
    String llOverride="override";
    String gFilesPath="resources/json/nsfw/lockedmen/";
    String gConfigFilePath=gFilesPath+"config.json";
    String gMainMenuFilePath=gFilesPath+"mainMenu.json";
    String gImageMenuFilePath=gFilesPath+"imagesMenu.json";
    interface Session{
        String session="session";
        interface Cookies{
            String  ASPSESSIONIDCWBDABSS="ASPSESSIONIDCWBDABSS",
                    l5FuserGUID="l%5FuserGUID",
                    lockedMEN="lockedMEN",
                    mutePM="mutePM",
                    wwf10lVisit="wwf10lVisit",
                    wwf10sID="wwf10sID";
        }
    }
    interface HTTPResponse{
        String result="result",exception="exception",code="code",message="message",body="body",status="status";
        interface GETSITESTATS{
            String profiles="profiles",members="members",photos="photos",forumposts="forumposts";
        }
        interface HOMEPAGEIMAGE{
                String l_GUID="l_GUID",img_lnick="img_lnick",img_filename="img_filename",imgcaption="imgcaption",imgheight="imgheight",imgwidth="imgwidth",
                        imgGUID="imgGUID",l_lastcountrycode="l_lastcountrycode",lproles="lproles",lplockedstatus="lplockedstatus",imgcreated="imgcreated";
        }
        interface HOMEPAGEFORUM{
            String topic_ID="topic_ID",forum="forum",subject="subject",author="author",link="link",Message_date="Message_date";
        }
        interface HOMEPAGESEARECHNICKRESULT{
            String flag="flag",nick="nick",icons="icons",GUID="GUID",imgCount="imgCount";
        }
        interface USERPROFILE{
            String nick="nick",l_GUID="l_GUID",roles="roles",khstatus="khstatus",lockedstatus="lockedstatus",sexuality="sexuality",lockedmax="lockedmax",devices="devices";
            String showprogress="showprogress",progressstartdate="progressstartdate",progressdays="progressdays",progresskeyholderGUID="progresskeyholderGUID",prkhnick="prkhnick";

        }
    }
    int intDefaultMinutes=5;
    interface  URL{
        String getStatus="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getsitestats&data=x&_=1641298320646",
        getHomepageNewImages="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getHomePageImagesJSON&data=new&_=1641298320643",
        getHomepageRandomImages="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getHomePageImagesJSON&data=random&_=1641298320644",
        getHomepageRecentForumPosts="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getRecentForumPosts&data=x&_=1641298320655",
        getUsersWithNick="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getbox&data=search&searchnick=!NICK&_=1641311252082";
        String imageUrl="https://www.lockedmen.net/profilesDB/!USERNICK/!IMAGEID.!EXT";
        String getUserProfile="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getUserProfileData&data=!ID&_=1641297277872",getUserProfile_a="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getUserProfileData&data={!ID}&_=1641297277872",getUserProfile_b="https://www.lockedmen.net/includes/ajax/LMajaxfunctions.asp?mode=getUserProfileData&data=!ID&_=1641297277872";
    }

}
