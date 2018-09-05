package com.spider.proxy;

import java.lang.reflect.Method;

/**
 * @author liuzhongkai
 */
public interface SimpleInvocationHandler {

    Object invoke(Method method, Object[] args) throws Throwable;
}
