package com.spider.autoswitching.event;

import com.ctrip.framework.apollo.ConfigService;

/**
 * @author liuzhongkai
 */
public class ApolloConfigChangeProxy implements ConfigChangeProxy {

    @Override
    public <T> void addChangeListener(Object namespace, ConfigChangeListener<T> listener) {
        ConfigService.getConfig(namespace.toString()).addChangeListener(changeEvent -> listener.onChange(changeEvent.changedKeys(), (T) changeEvent));
    }
}
