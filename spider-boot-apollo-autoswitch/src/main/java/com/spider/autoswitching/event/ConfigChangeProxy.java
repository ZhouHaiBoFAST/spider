package com.spider.autoswitching.event;

/**
 * 配置改变代理
 *
 * @author liuzhongkai
 */
public interface ConfigChangeProxy {

    <T> void addChangeListener(Object namespace, ConfigChangeListener<T> listener);
}
