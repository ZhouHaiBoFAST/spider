package com.spider.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Proxy;

/**
 * @author liuzhongkai
 */
public class Proxys {

    public static <T> T newProxyInstance(Class<T> clazz, SimpleInvocationHandler handler) {
        if (clazz.isInterface())
            return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz}, (proxy, method, args) -> handler.invoke(method, args));
        else {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(clazz);
            enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> handler.invoke(method, objects));
            return (T) enhancer.create();
        }
    }
}
