package br.com.rodrigoamora.transitorio.util;

import java.util.Date;

public class TimeUtil {

    public static double calculateTimeInMinutes(String hour) {
        Date date = new Date();
        date.setHours(Integer.valueOf(hour.split(":")[0]));
        date.setMinutes(Integer.valueOf(hour.split(":")[1]));
        date.setSeconds(Integer.valueOf(hour.split(":")[2]));

        Date date2 = new Date();

        long difference = date2.getTime() - date.getTime();
        double differenceInMinutes = (difference /1000) / 60;

        return differenceInMinutes;
    }

}
