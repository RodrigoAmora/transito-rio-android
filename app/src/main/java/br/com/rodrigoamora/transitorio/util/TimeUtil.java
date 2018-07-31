package br.com.rodrigoamora.transitorio.util;

import java.util.Date;

public class TimeUtil {

    public static double calculateTimeInMinutes(String hour) {
        Date consultedHour = new Date();
        consultedHour.setHours(Integer.valueOf(hour.split(":")[0]));
        consultedHour.setMinutes(Integer.valueOf(hour.split(":")[1]));
        consultedHour.setSeconds(Integer.valueOf(hour.split(":")[2]));

        Date currentHour = new Date();

        long difference = currentHour.getTime() - consultedHour.getTime();
        double differenceInMinutes = (difference /1000) / 60;

        return differenceInMinutes;
    }

}
