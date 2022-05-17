package nsfw.SexValues;

public interface iSexValues {
    String profileName="sexvalues";
    String llOverride="override";
    String gFilesPath="resources/json/nsfw/lockedmen/";
    String gConfigFilePath=gFilesPath+"config.json";
    String gMainMenuFilePath=gFilesPath+"mainMenu.json";
    String gImageMenuFilePath=gFilesPath+"imagesMenu.json";
    interface PROFILE{
        String score="score",
                started="started";
        interface Score{
            String  attract="attract",
                    drive="drive",
                    dominant="dominant",
                    deviance="deviance",
                    affect="affect";
        }
    }

}
