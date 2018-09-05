package com.spider.autoswitching;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.spider.proxy.AutosWitchingProxyHandler;
import com.spider.spring.SpringContextHolder;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;

import java.util.Set;

/**
 * @author liuzhongkai
 */
public interface AutoSwitchingBean<T> {

    /**
     * 创建的bean 名称
     */
    String beanName();

    /**
     * bean 类型
     */
    Class<T> type();

    /**
     * 监听源前缀
     */
    String monitorSourcePrefix();


    /**
     * 关闭策略
     */
    AsyncTerminateStrategy<T> asyncTerminateStrategy();

    /**
     * bean 构建者
     */
    Createable<T> supplier();

    /**
     * 代理对象
     */
    AutosWitchingProxyHandler<T> autosWitchingProxyHandler();


    /**
     * 监听的命名空间
     */
    default String namespace() {
        return ConfigConsts.NAMESPACE_APPLICATION;
    }

    /**
     * 默认的监听行为
     *
     * @see com.spider.autoswitching.event.ConfigChangeListener
     */
    default <V> void onChange(Set<String> changedKeys, V changeEvent) {
        for (String changedKey : changedKeys)
            if (changedKey.startsWith(monitorSourcePrefix())) {
                SpringContextHolder.applicationContext().publishEvent(new EnvironmentChangeEvent(changedKeys));
                asyncTerminateStrategy().terminate(autosWitchingProxyHandler().replace(supplier().create()));
                return;
            }
    }
}
