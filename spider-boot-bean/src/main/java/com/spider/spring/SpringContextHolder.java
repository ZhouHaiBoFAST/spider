package com.spider.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * spring bean获取器
 *
 * @author liuzhongkai
 */
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public SpringContextHolder() {
    }

    @Override
    public void setApplicationContext(ApplicationContext arg) {
        applicationContext = arg;

    }

    public static Object getBean(String beanId) {
        return applicationContext.getBean(beanId);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) throws BeansException {
        return applicationContext.getBeansWithAnnotation(clazz);
    }

    public static ApplicationContext applicationContext() {
        return applicationContext;
    }

}
