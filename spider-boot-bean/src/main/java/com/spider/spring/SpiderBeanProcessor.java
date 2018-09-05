package com.spider.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author liuzhongkai
 */
public abstract class SpiderBeanProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        doProcessBean(bean, beanName);
        for (Field field : clazz.getDeclaredFields())
            doProcessField(bean, field, beanName);
        for (Method method : clazz.getDeclaredMethods())
            doProcessMethod(bean, method, beanName);
        return bean;
    }

    /**
     * 字段处理回调
     *
     * @param bean     实例
     * @param field    字段对象
     * @param beanName 实例名称
     */
    protected void doProcessField(Object bean, Field field, String beanName) {
    }

    /**
     * 方法处理回调
     *
     * @param bean     实例
     * @param method   方法对象
     * @param beanName 实例名称
     */
    protected void doProcessMethod(Object bean, Method method, String beanName) {
    }

    /**
     * 类处理回调
     *
     * @param bean     实例
     * @param beanName 实例名称
     */
    protected void doProcessBean(Object bean, String beanName) {
    }
}
