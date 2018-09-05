package com.spider.autoswitching.annotation;

import com.google.common.collect.Sets;
import com.spider.autoswitching.AutoSwitchConfiguration;
import com.spider.autoswitching.AutoSwitchingBean;
import com.spider.autoswitching.AutoSwitchingBeanRegistry;
import com.spider.autoswitching.bind.ConfigurationPropertiesBinding;
import com.spider.autoswitching.bind.SpringBoot2ConfigurationPropertiesBinding;
import com.spider.autoswitching.event.ApolloConfigChangeProxy;
import com.spider.autoswitching.event.ConfigChangeProxy;
import com.spider.autoswitching.util.BeanRegistrationUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Set;

/**
 * @author liuzhongkai
 */
public class AutoSwitchingRegistrar implements ImportBeanDefinitionRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, ApolloConfigChangeProxy.class, ConfigChangeProxy.class.getSimpleName());
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringBoot2ConfigurationPropertiesBinding.class, ConfigurationPropertiesBinding.class.getSimpleName());
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, AutoSwitchingBeanRegistry.class, AutoSwitchingBeanRegistry.class.getSimpleName());
        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, AutoSwitchConfiguration.class, AutoSwitchConfiguration.class.getSimpleName());
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutoSwitching.class.getName()));
        Class<? extends AutoSwitchingBean<?>>[] values = (Class<? extends AutoSwitchingBean<?>>[]) attributes.getClassArray("value");
        Class<? extends AutoSwitchingBean<?>>[] bcs = (Class<? extends AutoSwitchingBean<?>>[]) attributes.getClassArray("beanClass");
        Set<Class<? extends AutoSwitchingBean<?>>> bsSet = null;
        if (values != null)
            bsSet = Sets.newHashSet(values);
        if (bsSet == null && bcs != null)
            bsSet = Sets.newHashSet(bcs);
        else if (bcs != null)
            Collections.addAll(bsSet, bcs);
        if (!CollectionUtils.isEmpty(bsSet))
            for (Class<? extends AutoSwitchingBean> clazz : bsSet) {
                BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, clazz, clazz.getSimpleName());
            }
    }
}
