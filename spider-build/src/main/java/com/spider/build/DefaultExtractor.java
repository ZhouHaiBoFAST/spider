package com.spider.build;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author liuzhongkai
 */
public class DefaultExtractor implements Extractor {

    private final Map<Class<?>, Class<?>> relevance = new ConcurrentHashMap<>();

    private final Map<Class<?>, Object> container = new ConcurrentHashMap<>();

    private Object[] segments = {new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object(), new Object()};

    public DefaultExtractor() {
        init();
    }

    private void init() {
        //        set(Extractor.class, this);}
    }


    @Override
    public <T> Optional<T> getInstance(Class<T> clazz) {
        Class<?> reality = relevance.get(clazz);
        Object o = null;
        if (reality != null) o = getAndPutLockInstance(reality);

        return Optional.ofNullable((T) o);
    }

    @Override
    public <T> void set(Class<T> virtualZ, Class<? extends T> realityZ) {
        set(virtualZ, realityZ, true);
    }

    @Override
    public <T> void set(Class<T> virtualZ, Class<? extends T> realityZ, boolean lazy) {
        if (realityZ.isInterface()) throw new IllegalArgumentException("actual type cannot be an interface");
        relevance.putIfAbsent(virtualZ, realityZ);
        if (virtualZ != realityZ)
            relevance.putIfAbsent(realityZ, realityZ);
        if (!lazy)
            getAndPutLockInstance(realityZ);

    }

    @Override
    public void set(Class<?> virtualZ, Object reality) {
        Class<?> actualClazz = reality.getClass();
        relevance.putIfAbsent(virtualZ, actualClazz);
        if (virtualZ != actualClazz) relevance.putIfAbsent(actualClazz, actualClazz);
        container.put(actualClazz, reality);
    }

    private <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object getAndPutLockInstance(Class<?> reality) {
        Object obj;
        if ((obj = container.get(reality)) == null)
            synchronized (segments[reality.hashCode() & (segments.length - 1)]) {
                if ((obj = container.get(reality)) == null) obj = newInstance(reality);
                container.put(reality, obj);
            }
        return obj;
    }

}

