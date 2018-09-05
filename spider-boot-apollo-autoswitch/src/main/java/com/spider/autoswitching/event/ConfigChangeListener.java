package com.spider.autoswitching.event;

import java.util.Set;

/**
 * 配置更改监听
 *
 * @param <T> 具体的监听回调对象
 * @author liuzhongkai
 */
public interface ConfigChangeListener<T> {

    public void onChange(Set<String> changedKeys, T changeEvent);

}
