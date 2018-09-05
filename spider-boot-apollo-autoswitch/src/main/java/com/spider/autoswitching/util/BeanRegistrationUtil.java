package com.spider.autoswitching.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * @author liuzhongkai
 */
public class BeanRegistrationUtil {


    public static boolean registerBeanDefinitionIfNotExists(BeanDefinitionRegistry registry, Class<?> beanClass, String beanName) {
        if (registry.containsBeanDefinition(beanName) || registry.isBeanNameInUse(beanName)) return false;
        BeanDefinition annotationProcessor = BeanDefinitionBuilder.genericBeanDefinition(beanClass).getBeanDefinition();
        registry.registerBeanDefinition(beanName, annotationProcessor);
        return true;
    }

}
