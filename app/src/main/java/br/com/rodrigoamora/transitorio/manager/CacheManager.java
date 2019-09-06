package br.com.rodrigoamora.transitorio.manager;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

public class CacheManager<T> {

    public CacheManager(Context context) {
        Hawk.init(context).build();
    }

    public T getCache(String key) {
        return Hawk.get(key);
    }

    public void saveCache(String key, T t) {
        Hawk.put(key, t);
    }

    public void deleteCache(String key) {
        Hawk.delete(key);
    }
}
