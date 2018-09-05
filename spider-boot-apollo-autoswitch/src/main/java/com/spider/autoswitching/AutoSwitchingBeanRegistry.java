package com.spider.autoswitching;

import com.spider.autoswitching.event.ConfigChangeProxy;
import com.spider.proxy.Proxys;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liuzhongkai
 */
public class AutoSwitchingBeanRegistry implements ApplicationContextAware {

    private ConfigChangeProxy configChangeProxy;

    private ConfigurableApplicationContext applicationContext;

    public AutoSwitchingBeanRegistry(ConfigChangeProxy configChangeProxy) {
        this.configChangeProxy = configChangeProxy;
    }

    public <T> boolean register(AutoSwitchingBean<T> autoSwitchingBean) {
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        if (!beanFactory.containsLocalBean(autoSwitchingBean.beanName())) {
            T proxyT = Proxys.newProxyInstance(autoSwitchingBean.type(), autoSwitchingBean.autosWitchingProxyHandler());
            beanFactory.registerSingleton(autoSwitchingBean.beanName(), proxyT);
        }
        configChangeProxy.addChangeListener(autoSwitchingBean.namespace(), autoSwitchingBean::onChange);
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
