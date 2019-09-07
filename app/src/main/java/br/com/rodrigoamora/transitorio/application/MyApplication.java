package br.com.rodrigoamora.transitorio.application;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class MyApplication extends Application {

    //private CacheManager cacheManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initiateHawk();
    }

    private void initiateHawk() {
        Hawk.init(this).build();
    }
}
