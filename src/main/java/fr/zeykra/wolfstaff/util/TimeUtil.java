package fr.zeykra.wolfstaff.util;

public class TimeUtil {

    public static String timeFormat(long enteredTime) {

        long time= enteredTime/20;
        int secs= (int) (time%60);
        time/=60;
        int mins= (int) (time%60);
        time/=60;
        int hours= (int) (time%24);
        time/=24;
        int days= (int) time;

        String res=days+"j"+hours+"h"+mins+"m"+secs+"s.";
        return res;
    }
}
