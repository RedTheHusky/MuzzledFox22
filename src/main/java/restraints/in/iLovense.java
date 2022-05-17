package restraints.in;

public interface iLovense {
    String stPermaLock ="Can't manipulate your restraints as they are permanently locked!",
            strLocked ="Can't manipulate your blindfold as !LOCKER has the keys!",
            strAccessSet2Pet ="Can't manipulate your blindfold as only youyr owner and sec-owners can do that.",
            strAccessSet2Public ="Can't manipulate your blindfold as your access set to public. While public everyone else can access it except you.",
            strCantDuePermalockTarget="Can't manipulate !TARGET's restraints as they are permanently locked!",
            strCantDueLockedTarget="Can't manipulate their blindfold as it is locked by !LOCKER",
            strCantDueAccess="Can't manipulate their blindfold due to their access setting.";
    String callbackURL_Main="http://92.83.177.246:8789/lovesense/callback",
            callbackURL_Debug="http://92.83.177.246:8788/lovesense/callback";
    //{"uid":"55","appVersion":"4.1.0","toys":{"de68826ef711":{"nickName":"","name":"hush","id":"de68826ef711","status":1}},"wssPort":"34568","httpPort":"34567","wsPort":"34567","appType":"remote","domain":"192-168-100-126.lovense.club","utoken":"55test","httpsPort":"34568","version":"101","platform":"android"}

    String keyLovense="lovense";
    String keyuid="uid",keyappVersion="appVersion",
            keywssPort="wssPort",keyhttpPort="httpPort",keywsPort="wsPort",keyappType="appType",keydomain="domain",keyhttpsPort="httpsPort",keyversion="version",keyplatform="platform",
            keytoys="toys",keyutoken="utoken";
    String keyToyNickName="nickName",keyToyName="name",keyToyId="id",keyToyStatus="status";
}
