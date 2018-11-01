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
     * @return 创建的bean 名称
     */
    String beanName();

    /**
     * @return bean 类型
     */
    Class<T> type();

    /**
     * @return 监听源前缀
     */
    String monitorSourcePrefix();


    /**
     * @return 关闭策略
     */
    AsyncTerminateStrategy<T> asyncTerminateStrategy();

    /**
     * @return bean 构建者
     */
    Createable<T> supplier();

    /**
     * @return 代理对象
     */
    AutosWitchingProxyHandler<T> autosWitchingProxyHandler();


    /**
     * @return 监听的命名空间
     */
    default String namespace() {
        return ConfigConsts.NAMESPACE_APPLICATION;
    }

    /**
     * 默认的监听行为
     *
     * @param changedKeys 更改的key集合
     * @param changeEvent 事件源
     * @param <V>         事件源V
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
