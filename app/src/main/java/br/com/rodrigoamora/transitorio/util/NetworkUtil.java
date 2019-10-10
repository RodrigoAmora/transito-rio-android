package br.com.rodrigoamora.transitorio.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

    public static boolean checkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            boolean mobileIsConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            boolean wifiIsConnected = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
            return !(!mobileIsConnected && !wifiIsConnected);
        }

        return false;
    }

}
