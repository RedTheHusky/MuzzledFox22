package models.ll;

import java.sql.Time;
import java.util.Date;

public interface llConverter {
    default int llMilliseconds2Days(long milliseconds, boolean isweeks){
        if(isweeks){
            return  (int) ((milliseconds / (1000*60*60*24)) % 7);
        }else{
            return (int) (milliseconds / (1000*60*60*24));
        }
    }
    default int llMilliseconds2Weeks(long milliseconds){
        return (int) (milliseconds / (1000*60*60*24*7));
    }

}
