package net.itimothy.elasticsearch.description.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class SimpleCache {

    private Map<String, Object> cache = new HashMap<>();

    public <T> T getOrResolve(String cacheKey, Callable<T> resolveFn) {
        if (!cache.containsKey(cacheKey)) {
            try {
                cache.put(cacheKey, resolveFn.call());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return (T) cache.get(cacheKey);
    }
}
