package br.com.rodrigoamora.transitorio.application;

import android.app.Application;

import br.com.rodrigoamora.transitorio.manager.CacheManager;

public class MyApplication extends Application {

    private CacheManager cacheManager;

    @Override
    public void onCreate() {
        super.onCreate();
        initiateHawk();
    }

    private void initiateHawk() {
        cacheManager = new CacheManager(this);
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }
}
