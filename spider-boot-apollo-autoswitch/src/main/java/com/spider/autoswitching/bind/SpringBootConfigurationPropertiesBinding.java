//package com.spider.autoswitching.bind;
//
//import org.springframework.beans.factory.BeanCreationException;
//import org.springframework.boot.bind.PropertiesConfigurationFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.core.env.PropertySources;
//
///**
// * spring boot 1.0 属性绑定
// *
// * @author liuzhongkai
// * @see org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor
// */
//public class SpringBootConfigurationPropertiesBinding implements ConfigurationPropertiesBinding, ApplicationContextAware {
//
//
//    private ConfigurableApplicationContext applicationContext;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
//    }
//
//    @Override
//    public <T> void bind(T target, String prefix) {
//        PropertiesConfigurationFactory factory = new PropertiesConfigurationFactory<>(target);
//        factory.setPropertySources(getPropertySources(applicationContext));
//        factory.setConversionService(getConversionService(applicationContext));
//        factory.setTargetName(prefix);
//        try {
//            factory.bindPropertiesToTarget();
//        } catch (Exception ex) {
//            throw new BeanCreationException("cannot bind properties", ex);
//        }
//    }
//
//    private PropertySources getPropertySources(ConfigurableApplicationContext applicationContext) {
//        return applicationContext.getEnvironment().getPropertySources();
//    }
//
//    private ConversionService getConversionService(ConfigurableApplicationContext applicationContext) {
//        return applicationContext.getBeanFactory().getConversionService();
//    }
//
//}
