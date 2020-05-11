package br.com.rodrigoamora.transitorio.util;

import android.content.Context;
import android.location.LocationManager;

public class GPSUtil {
    public static boolean gpsIsEnable(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
