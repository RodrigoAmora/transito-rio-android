package br.com.rodrigoamora.transitorio.manager;

import com.orhanobut.hawk.Hawk;

public class CacheManager<T> {
    public T getCache(String key) {
        return Hawk.get(key);
    }
    public void saveCache(String key, T t) {
        Hawk.put(key, t);
    }
    public void removeCache(String key) {
        Hawk.delete(key);
    }
}
