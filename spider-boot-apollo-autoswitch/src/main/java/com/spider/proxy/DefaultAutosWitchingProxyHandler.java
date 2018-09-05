package com.spider.proxy;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author liuzhongkai
 */
public class DefaultAutosWitchingProxyHandler<T> implements AutosWitchingProxyHandler<T> {

    private final AtomicReference<T> origin;

    public DefaultAutosWitchingProxyHandler(T initialValue) {
        this.origin = new AtomicReference<>(initialValue);
    }

    @Override
    public T get() {
        return origin.get();
    }

    @Override
    public T replace(T newValue) {
        return origin.getAndSet(newValue);
    }

    @Override
    public Object invoke(Method method, Object[] args) throws Throwable {
        return method.invoke(origin.get(), args);
    }
}
