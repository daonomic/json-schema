package io.daonomic.schema.json;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceCache {
    public static final InstanceCache INSTANCE = new InstanceCache();

    private final Map<Class, Object> cache = new ConcurrentHashMap<>();

    private InstanceCache() {
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> tClass) {
        return (T) cache.computeIfAbsent(tClass, key -> {
            try {
                return key.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
