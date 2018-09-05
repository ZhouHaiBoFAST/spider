package com.spider.autoswitching.bind;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;

import java.util.function.Consumer;

/**
 * spring boot 2.0 属性绑定
 *
 * @author liuzhongkai
 * @see org.springframework.boot.context.properties.ConfigurationPropertiesBinder
 */
public class SpringBoot2ConfigurationPropertiesBinding implements ConfigurationPropertiesBinding, ApplicationContextAware {

    private Binder binder;

    @Override
    public <T> void bind(T target, String prefix) {
        Bindable<T> bindable = Bindable.of((Class<T>) target.getClass()).withExistingValue(target);
        binder.bind(prefix, bindable);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        this.binder = new Binder(getConfigurationPropertySources(configurableApplicationContext), getPropertySourcesPlaceholdersResolver(configurableApplicationContext), getConversionService(configurableApplicationContext), getPropertyEditorInitializer(configurableApplicationContext));
    }

    private Iterable<ConfigurationPropertySource> getConfigurationPropertySources(ConfigurableApplicationContext applicationContext) {
        return ConfigurationPropertySources.from(applicationContext.getEnvironment().getPropertySources());
    }

    private PropertySourcesPlaceholdersResolver getPropertySourcesPlaceholdersResolver(ConfigurableApplicationContext applicationContext) {
        return new PropertySourcesPlaceholdersResolver(applicationContext.getEnvironment().getPropertySources());
    }

    private ConversionService getConversionService(ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBeanFactory().getConversionService();
    }

    private Consumer<PropertyEditorRegistry> getPropertyEditorInitializer(ConfigurableApplicationContext applicationContext) {
        return applicationContext.getBeanFactory()::copyRegisteredEditorsTo;
    }
}
