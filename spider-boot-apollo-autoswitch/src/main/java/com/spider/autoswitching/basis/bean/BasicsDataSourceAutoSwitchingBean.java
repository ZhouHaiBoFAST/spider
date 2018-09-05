package com.spider.autoswitching.basis.bean;

import com.spider.autoswitching.AsyncTerminateStrategy;
import com.spider.autoswitching.AutoSwitchingBean;
import com.spider.autoswitching.Createable;
import com.spider.autoswitching.SpinAsyncTerminateStrategy;
import com.spider.proxy.AutosWitchingProxyHandler;
import com.spider.proxy.DefaultAutosWitchingProxyHandler;

/**
 * @author liuzhongkai
 */
public abstract class BasicsDataSourceAutoSwitchingBean<T> implements AutoSwitchingBean<T> {

    protected final AutosWitchingProxyHandler<T> proxyHandler;

    protected final Createable<T> createable;

    protected final AsyncTerminateStrategy<T> asyncTerminateStrategy;

    protected final Class<T> type;

    public BasicsDataSourceAutoSwitchingBean(Createable<T> createable, AsyncTerminateStrategy.Closeable<T> closeable, Class<T> type) {
        this.createable = createable;
        proxyHandler = new DefaultAutosWitchingProxyHandler<>(createable.create());
        asyncTerminateStrategy = new SpinAsyncTerminateStrategy<>(type, closeable);
        this.type = type;
    }

    @Override
    public String beanName() {
        return "dataSource";
    }

    @Override
    public String monitorSourcePrefix() {
        return "spring.datasource";
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public AsyncTerminateStrategy<T> asyncTerminateStrategy() {
        return asyncTerminateStrategy;
    }

    @Override
    public Createable<T> supplier() {
        return createable;
    }

    @Override
    public AutosWitchingProxyHandler<T> autosWitchingProxyHandler() {
        return proxyHandler;
    }

}
