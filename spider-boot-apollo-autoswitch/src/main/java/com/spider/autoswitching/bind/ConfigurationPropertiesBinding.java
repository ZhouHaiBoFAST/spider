package com.spider.autoswitching.bind;

/**
 * @author liuzhongkai
 */
public interface ConfigurationPropertiesBinding {

    <T> void bind(T target, String prefix);

}
