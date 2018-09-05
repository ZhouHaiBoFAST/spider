package com.spider.build;

import java.util.Optional;

/**
 * @author liuzhongkai
 */
public interface Extractor {

    public <T> Optional<T> getInstance(Class<T> clazz);

    public <T> void set(Class<T> relyClazz, Class<? extends T> actualClazz);

    public <T> void set(Class<T> relyClazz, Class<? extends T> actualClazz, boolean lazy);

    public void set(Class<?> relyClazz, Object actual);
}
