package com.spider.proxy;

/**
 * @author liuzhongkai
 */
public interface AutosWitchingProxyHandler<T> extends SimpleInvocationHandler {

    T get();

    T replace(T newValue);
}
