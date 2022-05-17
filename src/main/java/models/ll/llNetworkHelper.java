package models.ll;

import java.net.URL;

public interface llNetworkHelper {
    String llLewdNoImage="https://cdn.discordapp.com/attachments/662556453260886016/662557550893334528/Discord_Uwu_Sweat.gif";
    default Boolean llIsValidURL(String url)
    {
        String fName="llIsValidURL";
        try {
            new URL(url).toURI();
            System.out.print(fName+".valid");
            return true;
        }
        catch (Exception e) {
            System.err.print(fName+".deleting op message exception:"+e);
            return false;
        }
    }
}
