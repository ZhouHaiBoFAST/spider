package com.spider.autoswitching;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 用于初始化 AutoSwitchConfiguration
 *
 * @author liuzhongkai
 */
public class AutoSwitchConfigurationInitializer implements BeanPostProcessor, Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    @Autowired
    private BeanFactory beanFactory;

    private final AtomicBoolean sign = new AtomicBoolean();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        if (!sign.getAndSet(true))
            this.beanFactory.getBean(AutoSwitchConfiguration.class);
        return bean;
    }

}