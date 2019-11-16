package br.com.rodrigoamora.transitorio.util;

import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class PermissionUtil {

    private static final int PERMISSION_REQUEST_CODE = 200;

    public static void requestPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

}
